package hexageeks.daftar.viewmodels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import hexageeks.daftar.R;
import hexageeks.daftar.models.Application;
import hexageeks.daftar.models.Stage;


public class WorkflowListAdapter extends RecyclerView.Adapter<WorkflowListAdapter.ViewHolder>{
    private Application data;
    private Context context;
    private String after = null;

    public WorkflowListAdapter(Application data, Context context) {
        this.data = data;
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.workflow_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Application application = data;
        final Stage stage = application.getWorkflow().getStage(position);

        holder.title.setText(stage.authName);
        int s = position+1;
        holder.desc.setText("Stage " + s + "/" + application.getStages());

        if (after != null) {
            holder.statusBtn.setText(after);
            if (after.equals("PENDING")) {
                    holder.statusBtn.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                    holder.statusBtn.setTextColor(context.getResources().getColor(R.color.black));
                } else
                    holder.statusBtn.setBackgroundColor(context.getResources().getColor(R.color.light_grey));
        } else if (application.getStatus() == -1) {
            if (application.getAssignedId().equals(stage.authId)) {
                after = "CANCELLED";
                holder.statusBtn.setText("REJECTED");
                holder.statusBtn.setBackgroundColor(context.getResources().getColor(R.color.red_dark));
            } else {
                holder.statusBtn.setText("SIGNED");
            }
        } else if (application.getStatus() == 1) {
            holder.statusBtn.setText("SIGNED");
        } else {
            if (application.getAssignedId().equals(stage.authId)) {
                after = "PENDING";
                holder.statusBtn.setText("PENDING");
                holder.statusBtn.setBackgroundColor(context.getResources().getColor(R.color.yellow));
                holder.statusBtn.setTextColor(context.getResources().getColor(R.color.black));
            } else {
                holder.statusBtn.setText("SIGNED");
            }
        }

    }


    @Override
    public int getItemCount() {
        return data.getStages();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView desc;
        public Button statusBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            this.title =  itemView.findViewById(R.id.workflow_title);
            this.desc = itemView.findViewById(R.id.workflow_desc);
            this.statusBtn = itemView.findViewById(R.id.workflow_action_btn);
        }
    }
}
