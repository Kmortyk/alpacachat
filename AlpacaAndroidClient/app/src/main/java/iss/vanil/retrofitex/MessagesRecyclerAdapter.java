package iss.vanil.retrofitex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import iss.vanil.retrofitex.entity.Message;

class ViewHolder extends RecyclerView.ViewHolder{

    private TextView nameTextView;
    private TextView messageTextView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.message_item_name);
        messageTextView = itemView.findViewById(R.id.message_item_text);
    }

    public void setName(String name) {
        if(nameTextView != null)
            nameTextView.setText(name);
    }

    public void setMessage(String text) {
        messageTextView.setText(text);
    }

}

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Message> messages;

    public MessagesRecyclerAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        Message curMessage = messages.get(i);
        View messageView;

        if(curMessage.isSentByUser()) {
            messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_this, null);
        }else{
            messageView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, null);
        }

        return  new ViewHolder(messageView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int i) {
        Message message = messages.get(i);
        vh.setName(message.name);
        vh.setMessage(message.message);
    }

    @Override
    public int getItemCount() { return messages.size(); }

}
