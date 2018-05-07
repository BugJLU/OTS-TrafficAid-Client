package org.bugjlu.ots_trafficaid_client.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.activity.MysupplyActivity;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

import java.util.List;
import java.util.zip.Inflater;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ViewHolder> {

    private List<Resource> resourceList;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View resourceView;
        TextView resourceText;
        public ViewHolder(View view)
        {
            super(view);
            resourceView = view;
            resourceText = (TextView) view.findViewById(R.id.item_resource_text);
        }
    }

    public ResourceAdapter(List<Resource> list) {
        resourceList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.resourceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.resourceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int positon = holder.getAdapterPosition();
                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                        .setTitle("删除对话框")
                        .setMessage("你确定要删除此项吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除后还要通知服务端进行更新

//                                MyService.resourceService.removeResource(resourceList.get(positon).getId());
//                                MysupplyActivity.updateResources();
                                notifyItemRemoved(positon);
                            }
                        }).create();
                dialog.show();
                return true;
            }
        });
        holder.resourceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource resource = resourceList.get(holder.getAdapterPosition());
                TextView textView = (TextView) parent.findViewById(R.id.resource_purpose);
                String type = "";
                switch (resource.getType())
                {
                    case 1:
                        type = "药物";
                    case 2:
                        type = "工具";
                    case 3:
                        type = "止血";
                }
                textView.setText("名称：" + resource.getName() + "\n" + "类型：" + type);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Resource resource = resourceList.get(position);
        holder.resourceText.setText(resource.getName());
    }

    @Override
    public int getItemCount() {
        return resourceList.size();
    }
}
