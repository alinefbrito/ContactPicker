package com.example.contactpicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //constantes
    private static final int RESULT_PICK_CONTACT = 1;
    private static final int RESULT_SELECT_IMAGE = 2;
    // Views
    TextView txtName, txtNumero;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instancia das Views
        txtName = (TextView) findViewById(R.id.txtContatoNome);
        txtNumero = (TextView) findViewById(R.id.txtContatoNum);
        img = (ImageView) findViewById(R.id.imageView);
    }

    public void selecionarContact(View view) {
        //inicia uma intent com ação de Seleção de contatos
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        //inicia uma activity que devolverá uma resposta
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    public void visualizarGaleria(View view) {
        //inicia uma intent com ação de Seleção de Imagens
        Intent intent =     new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        //inicia uma activity que devolverá uma resposta
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), RESULT_SELECT_IMAGE);
    }
//recebe a Resposta da activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //verifica se a resposta foi positiva (OK)
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //verifica qual a resposta recebida
                case RESULT_PICK_CONTACT:
                    //cria um cursor para navegar nas propriedades
                    Cursor cursor = null;
                    try {
                        String numero = null;
                        String nome = null;
                        // getData() conteúdo da URI com o contato selecionado
                        Uri uri = data.getData();
                        //pesquisa no conteúdo da URI
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
                        // index da coluna telefone
                        int telIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);
                        // index do nome do contato selecionado
                        int nomeIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        numero = cursor.getString(telIndex);
                        nome = cursor.getString(nomeIndex);
                        // Set the value to the textviews
                        txtName.setText("Nome: ".concat(nome));
                        txtNumero.setText("Telefone : ".concat(numero));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case RESULT_SELECT_IMAGE:
                    //recebe os dados da imagem
                        Uri imagemSelecionada = data.getData();
                        //define como source da imagem
                        img.setImageURI(imagemSelecionada);
                    break;

            }
        }
    }


}