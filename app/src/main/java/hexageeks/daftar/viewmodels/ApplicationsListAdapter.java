package hexageeks.daftar.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;

import androidx.recyclerview.widget.RecyclerView;
import hexageeks.daftar.R;
import hexageeks.daftar.models.Application;
import hexageeks.daftar.models.User;

import static hexageeks.daftar.utils.StorageUtils.downloadFileFromUrl;


public class ApplicationsListAdapter extends RecyclerView.Adapter<ApplicationsListAdapter.ViewHolder>{
    private Application[] data;


    public ApplicationsListAdapter(Application[] data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.applications_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Application application = data[position];

        holder.heading.setText(application.getName());
        holder.desc.setText(application.getDescription());
        holder.stageText.setText("" + application.getStage() + "/" + application.getStages());
        holder.stageProgress.setProgress((int)(((float) application.getStage())/application.getStages() * 100));
        holder.timestamp.setText(new SimpleDateFormat("MM/dd/yyyy hh:mm a").format(application.getTimestamp()));

        if (application.getCreatorId().equals(User.getInstance().id)) {
            holder.openBtn.setText("View");
        } else {
            holder.openBtn.setText("Sign");
        }

        holder.openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView heading;
        public TextView desc;
        public TextView stageText;
        public TextView timestamp;
        public ProgressBar stageProgress;
        public Button openBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            this.heading =  itemView.findViewById(R.id.application_item_heading);
            this.desc = itemView.findViewById(R.id.application_item_description);
            this.stageText = itemView.findViewById(R.id.application_item_stage_text);
            this.timestamp = itemView.findViewById(R.id.application_item_timestamp);
            this.stageProgress = itemView.findViewById(R.id.application_item_stage);
            this.openBtn = itemView.findViewById(R.id.application_item_btn);
        }
    }
}
