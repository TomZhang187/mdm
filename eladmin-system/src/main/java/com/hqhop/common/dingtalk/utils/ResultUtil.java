package com.hqhop.common.dingtalk.utils;


import com.hqhop.common.dingtalk.dingtalkVo.ResultVO;

/**
 *
 */
public class ResultUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    /**
     *  分页查询统一成果结果
     * @param object 返回的数据对象
     * @param totalCount 总记录数
     * @return
     */
    public static ResultVO success(Object object, Long totalCount) {

        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setTotalCount(totalCount);
        return resultVO;
    }


    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
