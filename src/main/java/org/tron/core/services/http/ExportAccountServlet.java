package org.tron.core.services.http;

import com.google.protobuf.ByteString;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.api.GrpcAPI.BytesMessage;
import org.tron.common.utils.AccountExporter;
import org.tron.common.utils.ByteArray;
import org.tron.core.Wallet;
import org.tron.core.db.Manager;
import org.tron.protos.Protocol.Block;

@Component
@Slf4j
public class ExportAccountServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      String expectBlockNumber = request.getParameter("block_number");
      AccountExporter.EXPORT_NUM.set(Long.parseLong(expectBlockNumber));
      String expectTimestamp = request.getParameter("time_stamp");
      AccountExporter.EXPORT_TIME.set(Long.parseLong(expectTimestamp));
      response.getWriter().println("Set successfully!\n"
          + "Please wait a moment and will dump the file on block height: " + expectBlockNumber + "\n"
          + "or near the time stamp: " + expectTimestamp + "\n"
          + "Log in to this machine to get the exported account files\n"
          + "Path: " + System.getProperty("user.dir") + "/*_accounts.csv");
    } catch (Exception e) {
      logger.debug("Exception: {}", e.getMessage());
      try {
        response.getWriter().println(Util.printErrorMsg(e));
      } catch (IOException ioe) {
        logger.debug("IOException: {}", ioe.getMessage());
      }
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
  }
}