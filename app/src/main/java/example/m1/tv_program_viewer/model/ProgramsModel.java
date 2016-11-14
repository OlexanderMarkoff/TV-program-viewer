package example.m1.tv_program_viewer.model;

import android.content.ContentValues;
import android.net.Uri;

import java.util.Date;

import example.m1.tv_program_viewer.model.api.ApiClient;
import example.m1.tv_program_viewer.model.data.Program;
import example.m1.tv_program_viewer.model.db.ContractClass;
import example.m1.tv_program_viewer.presenter.DataReadyListener;

import static example.m1.tv_program_viewer.Constants.NOW_DATE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PATH_PROGRAMS_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_CHANNEL_ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_DATE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_DESCRIPTION;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_TIME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_TITLE;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_CONTENT_URI;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_SCHEME;

/**
 * Created by M1 on 13.11.2016.
 */

public class ProgramsModel extends TvViewerBaseModel<Program> implements TvViewerModel {

    public ProgramsModel(DataReadyListener dataReadyListener) {
        super(dataReadyListener);
    }

    @Override
    protected void getFromNet(String... params) {
        tempParams = params;
        if (params == null || params.length == 0) {
            throw new IllegalArgumentException("request must contain at least an one param: [0] - channel ID (REQUIRED), [1] timestamp (optional)");
        }
        if (params == null || params.length < 2) {
            ApiClient.loadPrograms(this, params[0], "" + new Date().getTime());
        } else {
            ApiClient.loadPrograms(this, params[0], params[1]);
        }
    }

    @Override
    protected void handleError(Throwable t) {
        dataReadyListener.loadingError(t.getMessage());
    }

    @Override
    protected Uri getUri(String... params) {
        if (params == null || params.length == 0) {
            return PROGRAMS_CONTENT_URI;
        }
        return Uri.parse(PROGRAMS_SCHEME + ContractClass.AUTHORITY + PATH_PROGRAMS_ID + params[0] + "/" + (params.length == 2 ? params[1] : NOW_DATE));
    }

   @Override
    protected ContentValues objectToContentValues(Program object) {
        ContentValues cv = new ContentValues();
        cv.put(PROGRAMS_COLUMN_NAME_CHANNEL_ID, object.getChannelId());
        cv.put(PROGRAMS_COLUMN_NAME_DATE, object.getDate().replace("/", "-"));
        cv.put(PROGRAMS_COLUMN_NAME_TIME, object.getTime());
        cv.put(PROGRAMS_COLUMN_NAME_TITLE, object.getTitle());
        cv.put(PROGRAMS_COLUMN_NAME_DESCRIPTION, object.getDescription());
        return cv;
    }
}
