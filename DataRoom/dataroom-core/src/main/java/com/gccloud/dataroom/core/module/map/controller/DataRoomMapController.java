package com.gccloud.dataroom.core.module.map.controller;

import com.gccloud.common.utils.BeanConvertUtils;
import com.gccloud.common.vo.R;
import com.gccloud.dataroom.core.module.map.dto.DataRoomMapRepeatDTO;
import com.gccloud.dataroom.core.module.map.vo.DataRoomMapDataVO;
import com.gccloud.dataroom.core.module.map.vo.DataRoomMapVO;
import com.gccloud.dataroom.core.module.map.vo.MapChildVO;
import com.gccloud.dataroom.core.module.map.dto.DataRoomMapDTO;
import com.gccloud.dataroom.core.module.map.dto.MapSearchDTO;
import com.gccloud.dataroom.core.module.map.entity.DataRoomMapEntity;
import com.gccloud.dataroom.core.module.map.service.IDataRoomMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地图数据管理
 * @author hongyang
 * @version 1.0
 * @date 2023/8/31 16:52
 */
@Slf4j
@RestController
@RequestMapping("/bigScreen/map")
@Api(tags = "地图数据管理")
@ApiSort(value = 110)
public class DataRoomMapController {

    @Resource
    private IDataRoomMapService dataRoomMapService;


    @GetMapping("/list")
    @ApiOperation(value = "列表", position = 10, notes = "地图数据列表查询", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<DataRoomMapVO>> list(MapSearchDTO searchDTO) {
        List<DataRoomMapVO> list = dataRoomMapService.getList(searchDTO);
        return R.success(list);
    }


    @PostMapping("/add")
    @ApiOperation(value = "添加", position = 20, notes = "添加地图数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<String> add(@RequestBody DataRoomMapDTO mapDTO) {
        String id = dataRoomMapService.add(mapDTO);
        return R.success(id);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改", position = 30, notes = "修改地图数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> update(@RequestBody DataRoomMapDTO mapDTO) {
        dataRoomMapService.update(mapDTO);
        return R.success();
    }

    @PostMapping("/delete/{id}")
    @ApiOperation(value = "删除", position = 40, notes = "删除地图数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> delete(@PathVariable String id) {
        dataRoomMapService.delete(id);
        return R.success();
    }

    @PostMapping("/cascadingDelete/{id}")
    @ApiOperation(value = "级联删除", position = 50, notes = "级联删除地图数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> cascadingDelete(@PathVariable String id) {
        dataRoomMapService.cascadingDelete(id);
        return R.success();
    }

    @GetMapping("/getMapChildFromGeoJson/{id}")
    @ApiOperation(value = "根据地图id解析json中的子级", position = 60, notes = "根据地图id解析json中的子级", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<MapChildVO>> getMapChildFromGeoJson(@PathVariable String id) {
        List<MapChildVO> list = dataRoomMapService.getChildFromGeo(id);
        return R.success(list);
    }


    @GetMapping("/info/{id}")
    @ApiOperation(value = "详情", position = 70, notes = "地图数据详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<DataRoomMapVO> info(@PathVariable String id) {
        DataRoomMapEntity info = dataRoomMapService.info(id);
        DataRoomMapVO vo = BeanConvertUtils.convert(info, DataRoomMapVO.class);
        return R.success(vo);
    }


    @GetMapping("/data/{parentId}/{code}")
    @ApiOperation(value = "数据", position = 80, notes = "地图数据数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<DataRoomMapDataVO> data(@PathVariable String parentId, @PathVariable String code) {
        DataRoomMapEntity info = dataRoomMapService.getByParentIdAndCode(parentId, code);
        DataRoomMapDataVO vo = new DataRoomMapDataVO();
        // 如果没有找到该地图，或地图没有上传geojson数据，
        if (info == null) {
            vo.setAvailable(0);
            return R.success(vo);
        }
        vo.setAvailable(0);
        if (info.getUploadedGeoJson().equals(1)) {
            vo.setAvailable(1);
        }
        vo.setGeoJson(info.getGeoJson());
        vo.setId(info.getId());
        return R.success(vo);
    }


    @PostMapping("/upload")
    @ApiOperation(value = "上传", position = 90, notes = "上传地图数据", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Void> upload(@RequestBody DataRoomMapDTO mapDTO) {
        dataRoomMapService.uploadGeoJson(mapDTO.getId(), mapDTO.getGeoJson());
        return R.success();
    }


    @PostMapping("/repeat")
    @ApiOperation(value = "重复", position = 100, notes = "地图数据重复校验", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> repeat(@RequestBody DataRoomMapRepeatDTO mapDTO) {
        Boolean repeat = dataRoomMapService.repeatCheck(mapDTO);
        return R.success(repeat);
    }


    @GetMapping("/tree/{level}")
    @ApiOperation(value = "树", position = 110, notes = "地图数据树", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<List<DataRoomMapVO>> tree(@PathVariable String level) {
        int levelInt = 0;
        try {
            levelInt = Integer.parseInt(level);
        } catch (NumberFormatException e) {
            return R.error("地图层级参数错误");
        }
        List<DataRoomMapVO> list = dataRoomMapService.getAvailableTree(levelInt);
        return R.success(list);
    }



}