package page;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class LoggerHelper {
    private static Logger logger;

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("GrabFoodOrderTestLogger");
            try {
                // Tạo thư mục logs nếu chưa tồn tại
                File logDir = new File("logs");
                if (!logDir.exists()) {
                    logDir.mkdirs();
                }

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                FileHandler fileHandler = new FileHandler("logs/test_log_" + timestamp + ".log");
                fileHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false);
                logger.setLevel(Level.INFO);
            } catch (IOException e) {
                System.err.println("Cannot setup logger: " + e.getMessage());
            }
        }
        return logger;
    }
}

