package com.example.food.Sales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food.Adapter.PdfDocumentAdapter;
import com.example.food.Adapter.Ticket_Adapter;
import com.example.food.Getter.TicketGS;
import com.example.food.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Adjust_Charge extends AppCompatActivity {
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public UsbManager mUsbManager;
    private UsbDevice mDevice;
    private UsbDeviceConnection mConnection;
    private UsbInterface mInterface;
    private UsbEndpoint mEndPoint;
    private PendingIntent mPermissionIntent;
    private static Boolean forceCLaim = true;

    HashMap<String, UsbDevice> mDeviceList;
    Iterator<UsbDevice> mDeviceIterator;
    byte[] testBytes ,testBytes1;

    Ticket_Adapter ticketAdapter;
    RecyclerView recycler_print;
    TextView cash_total, cash_change;
    EditText change_received,table;
    Button charge;
    double finalamount;
    String total,list;
    int received;
    ArrayList<TicketGS> final_data;

    private ConstraintLayout llPdf;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust__charge);
        cash_total = (TextView) findViewById(R.id.cash_total);
        cash_change = (TextView) findViewById(R.id.cash_change);
        change_received = (EditText) findViewById(R.id.ET_cash_change);
        table=(EditText) findViewById(R.id.ET_table);
        charge = (Button) findViewById(R.id.btn_charge);
        recycler_print = (RecyclerView) findViewById(R.id.recycler_print);
        llPdf = (ConstraintLayout) findViewById(R.id.llPdf);

        Intent i = getIntent();
        total = i.getStringExtra("totals");
         final_data = i. getParcelableArrayListExtra("Total_list");


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_print.setLayoutManager(mLayoutManager);

        ticketAdapter = new Ticket_Adapter(final_data,this);
        recycler_print.setAdapter(ticketAdapter);
        ticketAdapter.notifyDataSetChanged();

//        list = i.getStringExtra("chargelist");
        if (total != null) {
            cash_total.setText(total);
            change_received.setText(total);
            int totalint = Integer.parseInt(total);
            int received = Integer.parseInt(change_received.getText().toString());
            finalamount = totalint - received;

        }

        change_received.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }



            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(change_received) != null) {
                    int totalint = Integer.parseInt(total);
                    try {
                        received = Integer.parseInt(change_received.getText().toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Cannot be 0");
                    }

                    finalamount = received - totalint;
                    cash_change.setText(String.valueOf(finalamount));
                }
            }
        });


//        charge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


//    PRINTER WORK

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mDeviceList = mUsbManager.getDeviceList();

        if (mDeviceList.size() > 0) {
            mDeviceIterator = mDeviceList.values().iterator();

            Toast.makeText(this, "Device List Size: " + String.valueOf(mDeviceList.size()), Toast.LENGTH_SHORT).show();


            String usbDevice = "";
            while (mDeviceIterator.hasNext()) {
                UsbDevice usbDevice1 = mDeviceIterator.next();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    usbDevice += "\n" +
                            "DeviceID: " + usbDevice1.getDeviceId() + "\n" +
                            "DeviceName: " + usbDevice1.getDeviceName() + "\n" +
                            "Protocol: " + usbDevice1.getDeviceProtocol() + "\n" +
                            "Product Name: " + usbDevice1.getProductName() + "\n" +
                            "Manufacturer Name: " + usbDevice1.getManufacturerName() + "\n" +
                            "DeviceClass: " + usbDevice1.getDeviceClass() + " - " + translateDeviceClass(usbDevice1.getDeviceClass()) + "\n" +
                            "DeviceSubClass: " + usbDevice1.getDeviceSubclass() + "\n" +
                            "VendorID: " + usbDevice1.getVendorId() + "\n" +
                            "ProductID: " + usbDevice1.getProductId() + "\n";
                }

                int interfaceCount = usbDevice1.getInterfaceCount();
                Toast.makeText(this, "INTERFACE COUNT: " + String.valueOf(interfaceCount), Toast.LENGTH_SHORT).show();

                mDevice = usbDevice1;

                Toast.makeText(this, "Device is attached", Toast.LENGTH_SHORT).show();
                //textView.setText(usbDevice);
            }
            //tx_view.setText(usbDevice);

            mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            registerReceiver(mUsbReceiver, filter);

            mUsbManager.requestPermission(mDevice, mPermissionIntent);
        } else {
            Toast.makeText(this, "Please attach printer via USB", Toast.LENGTH_SHORT).show();
        }
        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("size"," "+llPdf.getWidth() +"  "+llPdf.getWidth());
                bitmap = loadBitmapFromView(llPdf, llPdf.getWidth(), llPdf.getHeight());
                createPdf();
                pdf();


                print(mConnection, mInterface);
            }
        });

    }//oncreate-ends

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/pdffromScroll.pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF of Scroll is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF(){
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        File file = new File("/sdcard/pdffromScroll.pdf");
        if (file.exists())
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(Adjust_Charge.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void pdf(){
        PrintManager printManager=(PrintManager) getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(getApplicationContext(),"/sdcard/pdffromScroll.pdf");
//            printManager.print("/sdcard/pdffromScroll.pdf",printDocumentAdapter,new PrintAttributes().Builder().build());
            String jobName = getString(R.string.app_name) + " Document";
            // PrintJob printJob =
            printManager.print(jobName, printDocumentAdapter, new PrintAttributes.Builder().build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
//        try
//        {
//            PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(getApplicationContext(),"/sdcard/pdffromScroll.pdf" );
//        }
//        printManager.print("Document", printAdapter,new PrintAttributes.Builder().build());
//
//        catch (Exception e)
//        {
//            Logger.logError(e);
//        }
    }

//    private void createWebPrintJob() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
////            new UIHelper(this).showToast(R.string.min_19);
//            return;
//        } else {
//            // Get a PrintManager instance
//            PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
//
//            // Get a print adapter instance
//            PrintDocumentAdapter printAdapter;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                printAdapter = webView.createPrintDocumentAdapter(getString(R.string.asset_allocation));
//            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//                printAdapter = webView.createPrintDocumentAdapter();
//            } else {
//                // to satisfy lint.
//                return;
//            }
//
//            // Create a print job with name and adapter instance
//            String jobName = getString(R.string.app_name) + " Document";
//            // PrintJob printJob =
//            printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
//        }
//    }











    private void print(final UsbDeviceConnection connection, final UsbInterface usbInterface) {
        final String test = "Your Total"+ total + "\n\n"
                + "Change" + String.valueOf(finalamount) + "\n\n"
                + "Charged" + received
                + "Table" + table.getText().toString();
//        String tot = "";
//        for(int i=0; i<=final_data.size(); i++){
//        tot += final_data.get(i).getProduct_name();
//
//        }

        testBytes = test.getBytes();
//        testBytes1 = tot.getBytes();
        if (usbInterface == null) {
            Toast.makeText(this, "INTERFACE IS NULL", Toast.LENGTH_SHORT).show();
        } else if (connection == null) {
            Toast.makeText(this, "CONNECTION IS NULL", Toast.LENGTH_SHORT).show();
        } else if (forceCLaim == null) {
            Toast.makeText(this, "FORCE CLAIM IS NULL", Toast.LENGTH_SHORT).show();
        } else {

            connection.claimInterface(usbInterface, forceCLaim);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    byte[] cut_paper = {0x1D, 0x56, 0x41, 0x10};
                    connection.bulkTransfer(mEndPoint, testBytes, testBytes.length, 0);
                    connection.bulkTransfer(mEndPoint, testBytes1, testBytes1.length, 0);
                    connection.bulkTransfer(mEndPoint, cut_paper, cut_paper.length, 0);
                }
            });
            thread.run();
        }
    }


    final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            mInterface = device.getInterface(0);
                            mEndPoint = mInterface.getEndpoint(1);// 0 IN and  1 OUT to printer.
                            mConnection = mUsbManager.openDevice(device);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "PERMISSION DENIED FOR THIS DEVICE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };


    private String translateDeviceClass(int deviceClass) {

        switch (deviceClass) {

            case UsbConstants.USB_CLASS_APP_SPEC:
                return "Application specific USB class";

            case UsbConstants.USB_CLASS_AUDIO:
                return "USB class for audio devices";

            case UsbConstants.USB_CLASS_CDC_DATA:
                return "USB class for CDC devices (communications device class)";

            case UsbConstants.USB_CLASS_COMM:
                return "USB class for communication devices";

            case UsbConstants.USB_CLASS_CONTENT_SEC:
                return "USB class for content security devices";

            case UsbConstants.USB_CLASS_CSCID:
                return "USB class for content smart card devices";

            case UsbConstants.USB_CLASS_HID:
                return "USB class for human interface devices (for example, mice and keyboards)";

            case UsbConstants.USB_CLASS_HUB:
                return "USB class for USB hubs";

            case UsbConstants.USB_CLASS_MASS_STORAGE:
                return "USB class for mass storage devices";

            case UsbConstants.USB_CLASS_MISC:
                return "USB class for wireless miscellaneous devices";

            case UsbConstants.USB_CLASS_PER_INTERFACE:
                return "USB class indicating that the class is determined on a per-interface basis";

            case UsbConstants.USB_CLASS_PHYSICA:
                return "USB class for physical devices";

            case UsbConstants.USB_CLASS_PRINTER:
                return "USB class for printers";

            case UsbConstants.USB_CLASS_STILL_IMAGE:
                return "USB class for still image devices (digital cameras)";

            case UsbConstants.USB_CLASS_VENDOR_SPEC:
                return "Vendor specific USB class";

            case UsbConstants.USB_CLASS_VIDEO:
                return "USB class for video devices";

            case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
                return "USB class for wireless controller devices";

            default:
                return "Unknown USB class!";
        }
    }



}
