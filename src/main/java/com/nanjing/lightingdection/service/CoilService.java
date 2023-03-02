package com.nanjing.lightingdection.service;

import com.nanjing.lightingdection.dto.InputDisplay;
import gnu.io.PortInUseException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CoilService {

  public void excute(Integer id, Integer chanpinId)
      throws PortInUseException, NumberFormatException, UnknownHostException, IOException;

  public List<InputDisplay> getAllInputDisplay(Integer rejisterId);
}
