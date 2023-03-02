package com.nanjing.lightingdection.service.serviceImpl;

import com.nanjing.lightingdection.dao.ChanpinDao;
import com.nanjing.lightingdection.dao.DatameaningDao;
import com.nanjing.lightingdection.dto.InputDisplay;
import com.nanjing.lightingdection.dto.InputSon;
import com.nanjing.lightingdection.entity.Chanpin;
import com.nanjing.lightingdection.entity.Datameaning;
import com.nanjing.lightingdection.service.DatameaningService;
import com.nanjing.lightingdection.service.HoldingRejisterService;
import com.nanjing.lightingdection.tools.CRC;
import com.nanjing.lightingdection.tools.SerialPortManager;
import com.nanjing.lightingdection.tools.TcpPortManager;
import gnu.io.SerialPort;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoldingRejisterServiceImpl implements HoldingRejisterService {

  @Autowired private ChanpinDao chanpinDao;
  @Autowired private DatameaningDao datameaningDao;
  @Autowired private DatameaningService datameaningService;

  @Override
  public void excute(Integer id, Integer chanpinId, String input) throws Exception {
    Datameaning holdingRejister = datameaningDao.selectDatameaningById(id);
    Chanpin chanpin = chanpinDao.selectChanpinById(chanpinId);
    if (chanpin.getWay() == 1) {
      byte slaveId = CRC.parseIntToHex(chanpin.getSlaveId());
      byte functionId = CRC.parseIntToHex(holdingRejister.getFunctionId());

      int address = holdingRejister.getByteAddress();
      if (holdingRejister.getIsInput() == 0) {
        SerialPort serialPort = SerialPortManager.openPort(chanpin.getPortName(), 9600); // 得到从机的名称
        String func = holdingRejister.getFunctionCode();
        String func1 = func.substring(0, 2);
        String func2 = func.substring(2);
        byte[] byte1 = {
          slaveId,
          functionId,
          0x00,
          CRC.parseIntToHex(address - 1),
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
          CRC.parseIntToHex(address - 1),
          CRC.parseStringToHex(func1),
          CRC.parseStringToHex(func2),
          CRC.parseStringToHex(a1),
          CRC.parseStringToHex(a2)
        };
        SerialPortManager.sendToPort(serialPort, byte2);
        SerialPortManager.closePort(serialPort);
      } else {
        if (holdingRejister.getIsHex() == 0) { // 十进制数据输出
          SerialPort serialPort =
              SerialPortManager.openPort(chanpin.getPortName(), 9600); // 得到从机的名称
          // int a=Integer.parseInt(input.replace(" ", ""));
          int a = Integer.parseInt(input.replace(" ", ""));
          String b = Integer.toHexString(a);
          if (b.length() == 1) {
            b = "000" + b;
          }
          if (b.length() == 2) {
            b = "00" + b;
          }
          if (b.length() == 3) {
            b = "0" + b;
          }
          String func1 = b.substring(0, 2);
          String func2 = b.substring(2);
          // System.out.println("func1: "+func1+" func2:"+func2);
          byte[] byte1 = {
            slaveId,
            functionId,
            0x00,
            CRC.parseIntToHex(address - 1),
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
            CRC.parseIntToHex(address - 1),
            CRC.parseStringToHex(func1),
            CRC.parseStringToHex(func2),
            CRC.parseStringToHex(a1),
            CRC.parseStringToHex(a2)
          };
          SerialPortManager.sendToPort(serialPort, byte2);
          SerialPortManager.closePort(serialPort);
        } else { // 非十进制数据输出
          SerialPort serialPort =
              SerialPortManager.openPort(chanpin.getPortName(), 9600); // 得到从机的名称
          String c = input.replace(" ", "");
          System.out.println(c);
          String func1 = c.substring(0, 2);
          String func2 = c.substring(2);
          // byte test=CRC.parseStringToHex(func1);
          // System.out.println(test);
          byte[] byte1 = {
            slaveId,
            functionId,
            0x00,
            CRC.parseIntToHex(address - 1),
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
            CRC.parseIntToHex(address - 1),
            CRC.parseStringToHex(func1),
            CRC.parseStringToHex(func2),
            CRC.parseStringToHex(a1),
            CRC.parseStringToHex(a2)
          };
          SerialPortManager.sendToPort(serialPort, byte2);
          SerialPortManager.closePort(serialPort);
        }
      }
    } else {
      byte slaveId = CRC.parseIntToHex(chanpin.getSlaveId());
      byte functionId = CRC.parseIntToHex(holdingRejister.getFunctionId());
      int address = holdingRejister.getByteAddress();
      if (holdingRejister.getIsInput() == 0) {
        // SerialPort serialPort=SerialPortManager.openPort(chanpin.getPortName(),9600);//得到从机的名称
        String func = holdingRejister.getFunctionCode();
        String func1 = func.substring(0, 2);
        String func2 = func.substring(2);
        byte[] byte1 = {
          slaveId,
          functionId,
          0x00,
          CRC.parseIntToHex(address - 1),
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
          CRC.parseIntToHex(address - 1),
          CRC.parseStringToHex(func1),
          CRC.parseStringToHex(func2),
          CRC.parseStringToHex(a1),
          CRC.parseStringToHex(a2)
        };
        TcpPortManager.sendData(chanpin.getAddress(), chanpin.getPort(), byte2);
      } else {
        if (holdingRejister.getIsHex() == 0) {

          // SerialPort serialPort=SerialPortManager.openPort(chanpin.getPortName(),9600);//得到从机的名称
          int a = Integer.parseInt(input.replace(" ", ""));
          String b = Integer.toHexString(a);
          if (b.length() == 1) {
            b = "000" + b;
          }
          if (b.length() == 2) {
            b = "00" + b;
          }
          if (b.length() == 3) {
            b = "0" + b;
          }
          String func1 = b.substring(0, 2);
          String func2 = b.substring(2);
          byte[] byte1 = {
            slaveId,
            functionId,
            0x00,
            CRC.parseIntToHex(address - 1),
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
            CRC.parseIntToHex(address - 1),
            CRC.parseStringToHex(func1),
            CRC.parseStringToHex(func2),
            CRC.parseStringToHex(a1),
            CRC.parseStringToHex(a2)
          };
          // SerialPortManager.sendToPort(serialPort, byte2);
          // SerialPortManager.closePort(serialPort);

          TcpPortManager.sendData(chanpin.getAddress(), chanpin.getPort(), byte2);
        } else {
          // SerialPort serialPort=SerialPortManager.openPort(chanpin.getPortName(),9600);//得到从机的名称
          String c = input.replace(" ", "");
          System.out.println(c);
          String func1 = c.substring(0, 2);
          String func2 = c.substring(2);
          byte[] byte1 = {
            slaveId,
            functionId,
            0x00,
            CRC.parseIntToHex(address - 1),
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
            CRC.parseIntToHex(address - 1),
            CRC.parseStringToHex(func1),
            CRC.parseStringToHex(func2),
            CRC.parseStringToHex(a1),
            CRC.parseStringToHex(a2)
          };
          TcpPortManager.sendData(chanpin.getAddress(), chanpin.getPort(), byte2);
        }
      }
    }
  }

  @Override
  public List<InputDisplay> getAllInputDisplay(Integer rejisterId) {
    List<InputDisplay> list = new ArrayList<>();
    Chanpin a = chanpinDao.selectChanpinById(rejisterId);
    List<Datameaning> fathers = datameaningDao.getAllSons(rejisterId); // 获得所有的二级菜单
    for (int i = 0; i < fathers.size(); i++) {
      InputDisplay inputDisplay = new InputDisplay(null, null);
      inputDisplay.setName(fathers.get(i).getName());
      List<InputSon> varson = new ArrayList<>();
      List<Datameaning> sons = datameaningDao.getAllSons(fathers.get(i).getId());
      for (int j = 0; j < sons.size(); j++) {
        Datameaning holdingRejister = sons.get(j);
        InputSon input =
            new InputSon(
                holdingRejister.getId(), holdingRejister.getName(), holdingRejister.getIsInput());
        // input.setId(holdingRejister.getId());
        // input.setName(holdingRejister.getName());
        // input.setIsInput(holdingRejister.getIsInput());
        varson.add(input);
      }
      inputDisplay.setSons(varson);
      list.add(inputDisplay);
    }
    return list;
  }
}
