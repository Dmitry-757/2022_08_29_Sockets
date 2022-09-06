package org.dng;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.*;

public class AppContext {
    //** for logging ***
    //lets make anonymous class ;)
    private static final Formatter myFormatter = new Formatter() {
        @Override
        public String format(LogRecord record) {
//            return "Thread id="+record.getLongThreadID()+"::"+record.getSourceClassName()+"::"
//                    +record.getSourceMethodName()+"::"+"\n"
//                    +"    "+new Date(record.getMillis())+"::"
//                    +record.getMessage()+"\n";
//            return record.getSourceClassName()+"::"
//                    +record.getSourceMethodName()+"::"+"\n"
//                    +"    "+new Date(record.getMillis())+"::"
//                    +record.getMessage()+"\n";
            return "\n"+record.getLevel()+"\n"
                    +new Date(record.getMillis())+"\n"
//                    +record.getSourceClassName()+"::"+record.getSourceMethodName()+"\n"
                    +record.getMessage()+"\n";
        }
    };

    private static final Logger logger = getLogger();


    //**for logging ***
//    private static Logger getLogger(String className) {
    private static Logger getLogger() {
        Handler fileHandler = null;
        try {
            fileHandler = new FileHandler("d:\\myLog.log");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        assert fileHandler != null;
        fileHandler.setFormatter(myFormatter);
//        Logger logger = Logger.getLogger(className);
        Logger logger = Logger.getLogger("");
        //logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
        return logger;
    }

    public static Logger getMyLogger(String className){
        return logger;
    }

}
