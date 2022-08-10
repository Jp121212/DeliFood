package com.example.proyectoprogra;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.time.LocalDateTime;
import java.util.Random;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Cart extends AppCompatActivity  {
    Button home,account,search,cart,menos,mas;
    int contador1;
    int precio;
    TextView producto,price,price2,delivery,taxes,total,contador;
    ImageView img;
    int imagevalue;
    TextView myLabel;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        account = findViewById(R.id.account);
        search = findViewById(R.id.lupa);
        home = findViewById(R.id.home);
        cart = findViewById(R.id.cart);
        producto = findViewById(R.id.Producto);
        price = findViewById(R.id.Price);
        img = findViewById(R.id.imageView12);
        price2 = findViewById(R.id.producto8);
        menos = findViewById(R.id.btnmenos);
        mas = findViewById(R.id.btnmas);
        delivery = findViewById(R.id.delivery1);
        taxes = findViewById(R.id.taxes);
        total = findViewById(R.id.total);
        contador = findViewById(R.id.contador);
        contador1 = 0;
        Bundle extras = getIntent().getExtras();
        precio =Integer.parseInt(extras.getString("Price"));

        recibirDatos();

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contador1 ++;


                int subtotal= contador1 * precio;
                int resultado = Integer.valueOf(delivery.getText().toString())+Integer.valueOf(  taxes.getText().toString())+subtotal;
                price.setText(Integer.toString(subtotal));
                price2.setText(Integer.toString(subtotal));
                contador.setText(Integer.toString(contador1));
                total.setText(Integer.toString(resultado));
                }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(contador1 > 0){
                    contador1 --;
                    int subtotal= contador1 * precio;
                    price.setText(Integer.toString(subtotal));
                    price2.setText(Integer.toString(subtotal));
                    total.setText(Integer.toString(subtotal));
                }
                contador.setText(Integer.toString(contador1));
            }
        });
        contador.setText(Integer.toString(contador1));


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,Search.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this, Account.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,Menu.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cart.this,Cart.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra("EXTRA_MESSAGE");
        try {
            ImageButton openButton = (ImageButton) findViewById(R.id.openImpresora);
            Button sendButton = (Button) findViewById(R.id.Impresoraprint);
            ImageButton closeButton = (ImageButton) findViewById(R.id.cerrarImpresora);

            myLabel = (TextView) findViewById(R.id.label);
            findViewById(R.id.openImpresora).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        findBT();
                        openBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            findViewById(R.id.Impresoraprint).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sendData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            findViewById(R.id.cerrarImpresora).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        closeBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void sendData() throws IOException {
        try {
            Random Factura = new Random();
            Random Recibo = new Random();
            int f = Factura.nextInt(10000);
            int r = Recibo.nextInt(5000);
            LocalDateTime date = LocalDateTime.now();

            String msg =
            "Deli Food\n"+
            "Correo: facturaelectronica@delifood.co.cr\n"+
            "Gracias por tu pedido Mauricio\n"+
            "Numero de Factura: "+f+"\n"+
            "Numero de Recibo: "+r+"\n"+
            "Fecha: "+date+"\n"+
            "------------------------------\n"+
            "Producto: "+producto.getText().toString()+"\n"+
            "Cantidad: "+contador.getText().toString()+"\n"+
            "Subtotal: "+precio+"\n"+
            "Costo de envio: "+delivery.getText().toString()+"\n"+
            "Impuestos: "+taxes.getText().toString()+"\n"+
            "------------------------------\n"+
            "Total: "+total.getText().toString()+"\n"+
            "------------------------------\n";

            msg += "\n";

            mmOutputStream.write(msg.getBytes());

            myLabel.setText("Data sent.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingPermission")
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    Log.w("name",device.getName().toString());
                    if (device.getName().equals("BlueTooth Printer")) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            myLabel.setText("Bluetooth device found.");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("MissingPermission")
    void openBT() throws IOException {
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void recibirDatos(){
        Bundle extras = getIntent().getExtras();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            imagevalue = bundle.getInt("Image");
        }
        String d1 = extras.getString("Product");
        String d2 = extras.getString("Price");
        producto.setText(d1);
        price.setText(d2);
        price2.setText(d2);
        img.setImageResource(imagevalue);
    }
    public void monto(){
        int valor1 = Integer.parseInt(delivery.getText().toString());
        int valor2 = Integer.parseInt(taxes.getText().toString());
        int r = valor1 + valor2;
        total.setText(r);
    }
}

