package com.hqhop.modules.material.service.mapper;

import com.hqhop.modules.material.domain.Material;
import com.hqhop.modules.material.service.dto.MaterialDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-10-20T16:11:57+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
@Component
public class MaterialMapperImpl implements MaterialMapper {

    @Override
    public Material toEntity(MaterialDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Material material = new Material();

        material.setId( dto.getId() );
        material.setMaterialModel( dto.getMaterialModel() );
        material.setMaterialName( dto.getMaterialName() );
        material.setMaterialNumber( dto.getMaterialNumber() );

        return material;
    }

    @Override
    public MaterialDTO toDto(Material entity) {
        if ( entity == null ) {
            return null;
        }

        MaterialDTO materialDTO = new MaterialDTO();

        materialDTO.setId( entity.getId() );
        materialDTO.setMaterialModel( entity.getMaterialModel() );
        materialDTO.setMaterialName( entity.getMaterialName() );
        materialDTO.setMaterialNumber( entity.getMaterialNumber() );

        return materialDTO;
    }

    @Override
    public List<Material> toEntity(List<MaterialDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Material> list = new ArrayList<Material>( dtoList.size() );
        for ( MaterialDTO materialDTO : dtoList ) {
            list.add( toEntity( materialDTO ) );
        }

        return list;
    }

    @Override
    public List<MaterialDTO> toDto(List<Material> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<MaterialDTO> list = new ArrayList<MaterialDTO>( entityList.size() );
        for ( Material material : entityList ) {
            list.add( toDto( material ) );
        }

        return list;
    }
}
