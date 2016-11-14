package example.m1.tv_program_viewer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by M1 on 09.11.2016.
 */

public interface Constants {

    String FRAGMENT_TITLE_KEY = "fragment_title";
    String FRAGMENT_CHANNEL_ID_KEY = "channel_id";

    //REST section
    String ROOT_URL = "http://52.50.138.211:8080/ChanelAPI/";

    String DATE_FORMAT = "dd-MM-yyyy";
    String NOW_DATE = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date());

}
