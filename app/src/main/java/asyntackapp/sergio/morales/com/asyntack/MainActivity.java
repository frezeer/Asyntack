package asyntackapp.sergio.morales.com.asyntack;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //declaracion de Variables
        Button button;
        Button button2;
        Button button3;
        ProgressBar progressBar;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //identificar los botones por su id
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Asignar los eventos de Button
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    protected void UnSegundo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                for (int i = 0; i <= 10; i++) {
                    UnSegundo();
                }
                break;
            case R.id.button2:
                Hilos();
                break;
            case R.id.button3:
                EjemploAsyntask ejemploAsyncTask = new EjemploAsyntask();
                ejemploAsyncTask.execute();
                break;
            default:
                break;
        }
    }

    private void Hilos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    UnSegundo();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Tarea Larga Finalizada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }



    private class EjemploAsyntask extends AsyncTask<Void, Integer, Boolean> {

        ProgressBar progressBar;

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressbar);
            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            for (int i = 0; i <= 10; i++) {
                UnSegundo();
                publishProgress(i * 10);
                if (isCancelled()) {
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0].intValue());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getBaseContext(), "Tarea Larga ha sido Cancelada", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(resultado);
            if (resultado) {
                Toast.makeText(getBaseContext(), "Tarea Larga ha sido Finalizada", Toast.LENGTH_SHORT).show();
            }
        }

        public EjemploAsyntask() {
            super();
        }

    }
}
