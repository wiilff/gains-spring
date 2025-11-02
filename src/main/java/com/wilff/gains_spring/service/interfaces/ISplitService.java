package com.wilff.gains_spring.service.interfaces;

import com.wilff.gains_spring.dto.request.CreateSplitRequest;
import com.wilff.gains_spring.dto.request.WeeklyExercises;
import com.wilff.gains_spring.dto.response.SplitDTO;
import com.wilff.gains_spring.model.User;
import jakarta.transaction.Transactional;


import java.util.List;

public interface ISplitService {
    List<SplitDTO> getSplitsByUserId(int userId);
    SplitDTO createSplit(CreateSplitRequest dto, User user);
    SplitDTO updateSplit(int splitId, CreateSplitRequest dto);
}
