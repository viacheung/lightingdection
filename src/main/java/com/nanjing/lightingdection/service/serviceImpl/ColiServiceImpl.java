package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.DatameaningDao;
import com.nanjing.lightingdection.dto.InputDisplay;
import com.nanjing.lightingdection.dto.InputSon;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.service.CoilService;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.tools.CRC;
import com.nanjing.lightingdection.tools.SerialPortManager;
import com.nanjing.lightingdection.tools.TcpPortManager;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColiServiceImpl implements CoilService {

  @Autowired private ChanpinDao chanpinDao;
  @Autowired private DatameaningDao datameaningDao;
  @Autowired private DatameaningService datameaningService;

  @Override
  public void excute(Integer id, Integer chanpinId)
      throws PortInUseException, NumberFormatException, UnknownHostException, IOException {
    // Coil coil=coilDao.selectCoilById(id);
    Datameaning coil = datameaningDao.selectDatameaningById(id);
    Chanpin chanpin = chanpinDao.selectChanpinById(chanpinId);
    if (chanpin.getWay() == 1) {
      SerialPort serialPort = SerialPortManager.openPort(chanpin.getPortName(), 9600); // 得到从机的名称
      byte slaveId = CRC.parseIntToHex(chanpin.getSlaveId());
      byte functionId = CRC.parseIntToHex(coil.getFunctionId());

      int address = coil.getByteAddress();
      String func = coil.getFunctionCode();
      String func1 = func.substring(0, 2);
      String func2 = func.substring(2);
      byte[] byte1 = {
        slaveId,
        functionId,
        0x00,
        CRC.parseIntToHex(address),
        CRC.parseStringToHex(func1),
        CRC.parseStringToHex(func2)
      };
      String crcs = CRC.ReadToCRC(byte1);
      String a1 = crcs.substring(0, 2);
      String a2 = crcs.substring(2);
      byte[] byte2 = {
        slaveId,
        functionId,
        0x00,
        CRC.parseIntToHex(address),
        CRC.parseStringToHex(func1),
        CRC.parseStringToHex(func2),
        CRC.parseStringToHex(a1),
        CRC.parseStringToHex(a2)
      };
      SerialPortManager.sendToPort(serialPort, byte2);
      SerialPortManager.closePort(serialPort);
    } else {

      byte slaveId = CRC.parseIntToHex(chanpin.getSlaveId());
      byte functionId = CRC.parseIntToHex(coil.getFunctionId());
      // int address=coil.getStartRejister();
      int address = coil.getByteAddress();
      String func = coil.getFunctionCode();
      String func1 = func.substring(0, 2);
      String func2 = func.substring(2);
      byte[] byte1 = {
        slaveId,
        functionId,
        0x00,
        CRC.parseIntToHex(address),
        CRC.parseStringToHex(func1),
        CRC.parseStringToHex(func2)
      };
      String crcs = CRC.ReadToCRC(byte1);
      String a1 = crcs.substring(0, 2);
      String a2 = crcs.substring(2);
      byte[] byte2 = {
        slaveId,
        functionId,
        0x00,
        CRC.parseIntToHex(address),
        CRC.parseStringToHex(func1),
        CRC.parseStringToHex(func2),
        CRC.parseStringToHex(a1),
        CRC.parseStringToHex(a2)
      };
      TcpPortManager.sendData(chanpin.getAddress(), chanpin.getPort(), byte2);
    }
  }

  @Override
  public List<InputDisplay> getAllInputDisplay(Integer rejisterId) {
    List<InputDisplay> list = new ArrayList<>();
    // Chanpin chanpin=chanpinDao.selectChanpinById(chanpinId);
    System.out.println("rejisterId: " + rejisterId);
    // Chanpin a=chanpinDao.selectChanpinById(rejisterId);
    // System.out.println("线圈的输入： "+a);
    List<Datameaning> fathers = datameaningDao.getAllSons(rejisterId); // 获得所有的二级菜单
    for (int i = 0; i < fathers.size(); i++) {
      // InputDisplay inputDisplay=new InputDisplay();
      // inputDisplay.setName(fathers.get(i).getName());
      List<InputSon> varson = new ArrayList<>();
      List<Datameaning> sons = datameaningDao.getAllSons(fathers.get(i).getId());
      // System.out.println(sons);
      for (int j = 0; j < sons.size(); j++) {
        Datameaning coil = sons.get(j);
        InputSon input = new InputSon(coil.getId(), coil.getName(), 0);
        // input.setId(coil.getId());
        // input.setName(coil.getName());
        // input.setIsInput(0);
        varson.add(input);
      }
      InputDisplay inputDisplay = new InputDisplay(fathers.get(i).getName(), varson);
      // inputDisplay.setSons(varson);
      list.add(inputDisplay);
    }
    return list;
  }
}
