package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.dto.InputDisplay;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface HoldingRejisterService {

  public List<InputDisplay> getAllInputDisplay(Integer rejisterId);

  public void excute(Integer id, Integer chanpinId, String input) throws Exception;
}
