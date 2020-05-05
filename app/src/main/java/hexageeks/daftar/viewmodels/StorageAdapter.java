package hexageeks.daftar.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.recyclerview.widget.RecyclerView;
import hexageeks.daftar.R;
import hexageeks.daftar.models.StorageItem;
import hexageeks.daftar.utils.FileUtils;


public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.ViewHolder>{
    private StorageItem[] data;


    public StorageAdapter(StorageItem[] data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.storage_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StorageItem storageItem = data[position];

        // TODO: Bringup preview of FileType.PDF
        switch (storageItem.getFileType()) {
            case IMAGE:
                FileUtils.loadImageFromUrl(holder.previewImg.getContext(), holder.previewImg, storageItem.getFileUrl());
                break;

            default:
                holder.previewImg.setImageResource(R.drawable.pdf_file);
        }


        holder.fileName.setText(storageItem.getFileName());
        holder.desc.setText(storageItem.getFileDescription());

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: View Screen
            }
        });
        holder.downloadBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: Download Screen
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView previewImg;
        public TextView fileName;
        public TextView desc;
        public MaterialButton viewBtn;
        public MaterialButton downloadBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            this.previewImg =  itemView.findViewById(R.id.storage_item_img);
            this.fileName = itemView.findViewById(R.id.storage_item_title);
            this.desc = itemView.findViewById(R.id.storage_item_description);
            this.viewBtn = itemView.findViewById(R.id.storage_item_view_btn);
            this.downloadBtn = itemView.findViewById(R.id.storage_view_download_btn);
        }
    }
}
