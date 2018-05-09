package org.bugjlu.ots_trafficaid_client.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.bugjlu.ots_trafficaid_client.MyApplication;
import org.bugjlu.ots_trafficaid_client.R;
import org.bugjlu.ots_trafficaid_client.activity.MysupplyActivity;
import org.bugjlu.ots_trafficaid_client.localdata.MyService;
import org.bugjlu.ots_trafficaid_client.remote.remote_object.Resource;

import java.util.List;

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
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        final ViewHolder fholder = holder;

        holder.resourceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MyApplication.getContext(),"tru",Toast.LENGTH_SHORT).show();
                final int positon = fholder.getAdapterPosition();
                Resource res = resourceList.get(positon);
                String rmsg = "名称："+res.getName() +"\n" +
                        "类型："+(res.getType() == null?"" :MyService.getTypeName(res.getType())) +"\n"+
                        "长按删除";

                if (MysupplyActivity.text != null) {
                    MysupplyActivity.text.setText(rmsg);
                }
            }
        });
        holder.resourceView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int positon = fholder.getAdapterPosition();
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
                                final Integer id = resourceList.get(positon).getId();
                                resourceList.remove(positon);
                                notifyItemRemoved(positon);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Boolean b = MyService.resourceService.removeResource(id);
//                                        if (b) {
//
//                                        }
                                    }
                                }).start();
                            }
                        }).create();
                dialog.show();
                return true;
            }
        });
//        return holder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resource, parent, false);
        final ViewHolder holder = new ViewHolder(view);
//        holder.resourceView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.getContext(),"tru",Toast.LENGTH_SHORT).show();
//                final int positon = holder.getAdapterPosition();
//                Resource res = resourceList.get(positon);
//                String rmsg = "名称："+res.getName() +"\n" +
//                        "类型："+(res.getType() == null?"" :MyService.getTypeName(res.getType())) +"\n"+
//                        "长按删除";
//
//                if (MysupplyActivity.text != null) {
//                    MysupplyActivity.text.setText(rmsg);
//                }
//            }
//        });
//        holder.resourceView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                final int positon = holder.getAdapterPosition();
////
////                AlertDialog dialog = new AlertDialog.Builder(v.getContext())
////                        .setTitle("删除对话框")
////                        .setMessage("你确定要删除此项吗？")
////                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.dismiss();
////                            }
////                        })
////                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                //删除后还要通知服务端进行更新
////                                resourceList.remove(positon);
////                                new Thread(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        MyService.resourceService.removeResource(resourceList.get(positon).getId());
////                                    }
////                                }).start();
////                                notifyItemRemoved(positon);
////                            }
////                        }).create();
////                dialog.show();
//                return true;
//            }
//        });
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
