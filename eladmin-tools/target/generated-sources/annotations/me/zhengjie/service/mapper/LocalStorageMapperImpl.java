package me.zhengjie.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.service.dto.LocalStorageDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-14T13:27:30+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class LocalStorageMapperImpl implements LocalStorageMapper {

    @Override
    public LocalStorage toEntity(LocalStorageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LocalStorage localStorage = new LocalStorage();

        localStorage.setId( dto.getId() );
        localStorage.setRealName( dto.getRealName() );
        localStorage.setName( dto.getName() );
        localStorage.setSuffix( dto.getSuffix() );
        localStorage.setType( dto.getType() );
        localStorage.setSize( dto.getSize() );
        localStorage.setOperate( dto.getOperate() );
        localStorage.setCreateTime( dto.getCreateTime() );
        localStorage.setUpdateTime( dto.getUpdateTime() );

        return localStorage;
    }

    @Override
    public LocalStorageDTO toDto(LocalStorage entity) {
        if ( entity == null ) {
            return null;
        }

        LocalStorageDTO localStorageDTO = new LocalStorageDTO();

        localStorageDTO.setId( entity.getId() );
        localStorageDTO.setRealName( entity.getRealName() );
        localStorageDTO.setName( entity.getName() );
        localStorageDTO.setSuffix( entity.getSuffix() );
        localStorageDTO.setType( entity.getType() );
        localStorageDTO.setSize( entity.getSize() );
        localStorageDTO.setOperate( entity.getOperate() );
        localStorageDTO.setCreateTime( entity.getCreateTime() );
        localStorageDTO.setUpdateTime( entity.getUpdateTime() );

        return localStorageDTO;
    }

    @Override
    public List<LocalStorage> toEntity(List<LocalStorageDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<LocalStorage> list = new ArrayList<LocalStorage>( dtoList.size() );
        for ( LocalStorageDTO localStorageDTO : dtoList ) {
            list.add( toEntity( localStorageDTO ) );
        }

        return list;
    }

    @Override
    public List<LocalStorageDTO> toDto(List<LocalStorage> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LocalStorageDTO> list = new ArrayList<LocalStorageDTO>( entityList.size() );
        for ( LocalStorage localStorage : entityList ) {
            list.add( toDto( localStorage ) );
        }

        return list;
    }
}
