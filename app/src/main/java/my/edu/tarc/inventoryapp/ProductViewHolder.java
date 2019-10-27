package my.edu.tarc.inventoryapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription, txtProductPrice,txtProductBarcode, txtProductQty, txtProductExpDate,txtProductCat;
    public ImageView ProductimageView;
    public ItemClickListner listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        ProductimageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtProductBarcode = (TextView) itemView.findViewById(R.id.product_Barcode);
        txtProductQty = (TextView) itemView.findViewById(R.id.product_Qty);
        txtProductExpDate = (TextView) itemView.findViewById(R.id.product_ExpDate);
        txtProductCat = (TextView) itemView.findViewById(R.id.product_Category);

    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}


