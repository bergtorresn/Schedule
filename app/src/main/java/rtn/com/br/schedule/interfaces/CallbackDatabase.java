package rtn.com.br.schedule.interfaces;

import com.google.android.gms.tasks.Task;

public interface CallbackDatabase {

    void onCallback(Task<Void> task);
}
