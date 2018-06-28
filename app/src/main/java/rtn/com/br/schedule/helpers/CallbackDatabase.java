package rtn.com.br.schedule.helpers;

import com.google.android.gms.tasks.Task;

public interface CallbackDatabase {

    void onCallback(Task<Void> task);
}
