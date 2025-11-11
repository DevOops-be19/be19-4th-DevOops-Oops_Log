package com.devoops.oopslog.achivement.query.service;

import com.devoops.oopslog.achivement.query.dto.*;
import com.devoops.oopslog.achivement.query.mapper.AchivementReadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchivementReadServiceImpl implements AchivementReadService {

    private AchivementReadMapper achivementReadMapper;

    @Autowired
    public AchivementReadServiceImpl(AchivementReadMapper achivementReadMapper) {
        this.achivementReadMapper = achivementReadMapper;
    }


    @Override
    public AchivementSummaryDTO getUserAchivementSummary(Long userId, int year, int month) {
        List<OopsRecordCountDTO> topOopsRecords = achivementReadMapper.selectDailyUserOopsRecord(userId, year, month);
        List<OohRecordCountDTO> topOohRecords =  achivementReadMapper.selectDailyUserOohRecord(userId, year, month);
        int oopsCount = achivementReadMapper.countOopsRecord(userId);
        int oohCount = achivementReadMapper.countOohRecord(userId);
        List<TagCountDTO> topOopsTags = achivementReadMapper.selectTopOopsTagByMonth(userId, year, month);
        List<TagCountDTO> topOohTags = achivementReadMapper.selectTopOohTagByMonth(userId, year, month);
        List<FindYearMonthDTO> findYearOops = achivementReadMapper.findYearMonthOopsById(userId);
        List<FindYearMonthDTO> findYearOoh = achivementReadMapper.findYearMonthOohById(userId);

        AchivementSummaryDTO summary = new AchivementSummaryDTO();
        summary.setOopsRecords(topOopsRecords);
        summary.setOohRecords(topOohRecords);
        summary.setOopsCount(oopsCount);
        summary.setOohCount(oohCount);
        summary.setTopOopsTags(topOopsTags);
        summary.setTopOohTags(topOohTags);
        summary.setFindYearOops(findYearOops);
        summary.setFindYearOoh(findYearOoh);

        return summary;
    }
}
