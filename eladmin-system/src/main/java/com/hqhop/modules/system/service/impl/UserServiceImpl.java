package com.hqhop.modules.system.service.impl;

import com.hqhop.exception.EntityExistException;
import com.hqhop.exception.EntityNotFoundException;
import com.hqhop.modules.monitor.service.RedisService;
import com.hqhop.modules.system.domain.Employee;
import com.hqhop.modules.system.domain.Role;
import com.hqhop.modules.system.domain.User;
import com.hqhop.modules.system.domain.UserAvatar;
import com.hqhop.modules.system.repository.EmployeeRepository;
import com.hqhop.modules.system.repository.RoleRepository;
import com.hqhop.modules.system.repository.UserAvatarRepository;
import com.hqhop.modules.system.repository.UserRepository;
import com.hqhop.modules.system.service.DeptService;
import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.RoleSmallDTO;
import com.hqhop.modules.system.service.dto.UserDTO;
import com.hqhop.modules.system.service.dto.UserQueryCriteria;
import com.hqhop.modules.system.service.mapper.UserMapper;
import com.hqhop.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserAvatarRepository userAvatarRepository;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleRepository roleRepository;



    @Value("${file.avatar}")
    private String avatar;

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public List<UserDTO> queryAll(UserQueryCriteria criteria) {
        List<User> users = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
        return userMapper.toDto(users);
    }

    @Override
    public UserDTO findById(long id) {
        Optional<User> user = userRepository.findById(id);
        ValidationUtil.isNull(user,"User","id",id);
        return userMapper.toDto(user.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO create(User resources) {
        // 唯一值判断
        if(userRepository.findByUsername(resources.getUsername())!=null){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

//        if(userRepository.findByEmail(resources.getEmail())!=null){
//            throw new EntityExistException(User.class,"email",resources.getEmail());
//        }

//        if(userRepository.findByDduserid(resources.getDduserid())!=null){
//            throw new EntityExistException(User.class,"dduserid",resources.getEmail());
//        }
//
//        if(userRepository.findByEmpnum(resources.getEmpnum())!=null){
//            throw new EntityExistException(User.class,"empnum",resources.getEmail());
//        }

        // 默认密码 123456，此密码是加密后的字符
        resources.setPassword("e10adc3949ba59abbe56e057f20f883e");
        if(resources.getEmployee()!=null){
            Employee employee = employeeRepository.findByEmployeeCode(resources.getEmployee().getEmployeeCode());
            resources.setEmployee(employee);
        }
        return userMapper.toDto(userRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        Optional<User> userOptional = userRepository.findById(resources.getId());
        ValidationUtil.isNull(userOptional,"User","id",resources.getId());

        User user = userOptional.get();

        User user1 = userRepository.findByUsername(user.getUsername());
//        User user2 = userRepository.findByEmail(user.getEmail());
//        User user3 = userRepository.findByDduserid(user.getDduserid());
//        User user4 = userRepository.findByEmpnum(user.getEmpnum());

        if(user1 !=null&&!user.getId().equals(user1.getId())){
            throw new EntityExistException(User.class,"username",resources.getUsername());
        }

//        if(user2!=null&&!user.getId().equals(user2.getId())){
//            throw new EntityExistException(User.class,"email",resources.getEmail());
//        }
//
//        if(user3!=null&&!user.getId().equals(user3.getId())){
//            throw new EntityExistException(User.class,"dduserid",resources.getEmail());
//        }
//        if(user4!=null&&!user.getId().equals(user4.getId())){
//            throw new EntityExistException(User.class,"empnum",resources.getEmail());
//        }
        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisService.delete(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisService.delete(key);
        }

        user.setUsername(resources.getUsername());
//        user.setEmail(resources.getEmail());
//        user.setDduserid(resources.getDduserid());
//        user.setEmpnum(resources.getEmpnum());
        user.setEnabled(resources.getEnabled());
        //赋予角色
        user.setRoles(resources.getRoles());

//        user.setJob(resources.getJob());
//        user.setPhone(resources.getPhone());
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByName(String userName) {
        User user = null;
        user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {

            UserDTO userDTO = userMapper.toDto(user);
            if(userDTO.getEmployee()!=null){
                Employee employee = employeeRepository.findByDingId(userDTO.getEmployee().getDingId());
                userDTO.setDepts(employee.getDeptsSet());
                userDTO.setBelongFiliales(deptService.getBelongFiliale(employee.getDeptsSet()));
            }

            return userDTO;
        }
    }


    @Override
    public UserDTO findByDingId(String userName) {
        User user = null;
        user = userRepository.findByUsername(userName);


        UserDTO userDTO = new UserDTO();
        if(user==null){
              userDTO = createUserByDingId(userName);
              }else {
            userDTO = userMapper.toDto(user);
              }
            if(userDTO.getEmployee()!=null) {
                Employee employee = employeeRepository.findByDingId(userDTO.getEmployee().getDingId());
                userDTO.setDepts(employee.getDeptsSet());
                userDTO.setBelongFiliales(deptService.getBelongFiliale(employee.getDeptsSet()));
            }


            return userDTO;

    }

    @Override
    public   UserDTO createUserByDingId(String dingId){

        Employee byDingId = employeeRepository.findByDingId(dingId);
        if(byDingId == null){
            throw new EntityNotFoundException(Employee.class, "employee", byDingId);
        } else {

            User user = new User();
            user.setEmployee(byDingId);
            user.setUsername(dingId);
            user.setEnabled(true);
            Role role= roleRepository.findByName("客商物料用户");
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
            UserDTO userDTO = create(user);
            return userDTO;

        }


    }












    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        userRepository.updatePass(username,pass,new Date());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(MultipartFile multipartFile) {
        User user = userRepository.findByUsername(SecurityUtils.getUsername());
        UserAvatar userAvatar = user.getUserAvatar();
        String oldPath = "";
        if(userAvatar != null){
           oldPath = userAvatar.getPath();
        }
        File file = FileUtil.upload(multipartFile, avatar);
        userAvatar = userAvatarRepository.save(new UserAvatar(userAvatar,file.getName(), file.getPath(), FileUtil.getSize(multipartFile.getSize())));
        user.setUserAvatar(userAvatar);
        userRepository.save(user);
        if(StringUtils.isNotBlank(oldPath)){
            FileUtil.del(oldPath);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username,email);
    }

    @Override
    public void download(List<UserDTO> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDTO userDTO : queryAll) {
            List roles = userDTO.getRoles().stream().map(RoleSmallDTO::getName).collect(Collectors.toList());
            Map map = new LinkedHashMap();
            map.put("用户名", userDTO.getUsername());
            map.put("头像", userDTO.getAvatar());
            map.put("邮箱", userDTO.getEmail());
            map.put("状态", userDTO.getEnabled() ? "启用" : "禁用");
            map.put("手机号码", userDTO.getPhone());
            map.put("角色", roles);
            map.put("岗位", userDTO.getJob().getName());
            map.put("最后修改密码的时间", userDTO.getLastPasswordResetTime());
            map.put("创建日期", userDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }


    //对比用户姓名是否同名
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String compareName(String name) {
        User user1 = userRepository.findByUsername(name);
        if(user1 !=null){
           return "yes";
        }
         return "no";
    }







}
