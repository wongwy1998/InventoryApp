package my.edu.tarc.inventoryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    public static EditText inputBarcode;
    private String proName,proBarcode,proPrice,proDesc,proExpDate,proQty,spinnerCat;
    private EditText inputName, inputDesc, inputPrice, inputQty, inputExpDate;
    private Button btnConfirm, btnCancel, btnScan;
    private Spinner spinnerCategory;
    private ImageView upImage;
    private StorageReference iStorageRef;
    private Uri ImageUri;
    private String saveCurrentDate,saveCurrentTime;
    private String downloadImageUrl,productRandomKey;
    private DatabaseReference databaseProduct;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        iStorageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        databaseProduct = FirebaseDatabase.getInstance().getReference("Product");
        inputBarcode = (EditText) findViewById(R.id.inputBarcode);
        inputName = (EditText) findViewById(R.id.inputpName);
        inputDesc = (EditText) findViewById(R.id.inputDesc);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputQty = (EditText) findViewById(R.id.inputQty);
        inputExpDate = (EditText) findViewById(R.id.inputDate);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnScan = (Button) findViewById(R.id.btnScanCode);
        upImage = (ImageView) findViewById(R.id.productimage);
        spinnerCategory = (Spinner) findViewById(R.id.pcategory);
        loadingBar = new ProgressDialog(this);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();

            }
        });

        upImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this , DisplayProductActivity.class );
                startActivity(intent);
            }
        });

    }



    private void OpenGallery() {
        Intent fintent = new Intent();
        fintent.setType("image/*");
        fintent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(fintent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            ImageUri = data.getData();
            upImage.setImageURI(ImageUri);
        }
    }

    private void ValidateProduct() {


        proName = inputName.getText().toString();
        proBarcode = inputBarcode.getText().toString();
        proPrice = inputPrice.getText().toString();
        proDesc = inputDesc.getText().toString();
        proExpDate = inputExpDate.getText().toString();
        proQty = inputQty.getText().toString();
        spinnerCat = spinnerCategory.getSelectedItem().toString();


        if (TextUtils.isEmpty(proName)) {
            Toast.makeText(this, "Please fill in the Product Name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proBarcode)) {
            Toast.makeText(this, "Please scan in the Product Barcode", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proDesc)) {
            Toast.makeText(this, "Please fill in the Product Description", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proPrice)) {
            Toast.makeText(this, "Please fill in the Product Price", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proQty)) {
            Toast.makeText(this, "Please fill in the Product Quantity", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(proExpDate)) {
            Toast.makeText(this, "Please fill in the Product Expire Date", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(spinnerCat)) {
            Toast.makeText(this, "Please select the Product Category", Toast.LENGTH_SHORT).show();

        } else if (ImageUri == null) {
            Toast.makeText(this, "Please select the Product Image", Toast.LENGTH_SHORT).show();

        }else {
            StoreProductInfo();

        }

    }

    private void StoreProductInfo() {

        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Please Wait, we are adding the new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MM,YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = iStorageRef.child(ImageUri.getLastPathSegment() +"  "+ productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AddProductActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AddProductActivity.this,"Product Image URL added successfully",Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();

                        }
                    }


                });

            }

        });
    }
       private void saveProductInfoToDatabase(){
            HashMap<String,Object> productMap = new HashMap<>();
            productMap.put("Barcode",proBarcode);
            productMap.put("date", saveCurrentDate);
            productMap.put("Time",saveCurrentTime);
            productMap.put("Name",proName);
            productMap.put("Category",spinnerCat);
            productMap.put("Price",proPrice);
            productMap.put("Desc",proDesc);
            productMap.put("Qty",proQty);
            productMap.put("ExpDate",proExpDate);
            productMap.put("Image",downloadImageUrl);


            databaseProduct.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Intent intent = new Intent(AddProductActivity.this , AddProductActivity.class );
                        startActivity(intent);

                        loadingBar.dismiss();
                        Toast.makeText(AddProductActivity.this,"Product added successfully",Toast.LENGTH_SHORT).show();


                    }
                    else{
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AddProductActivity.this,"Error :" +message,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}