package iss.vanil.retrofitex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.List;

import iss.vanil.retrofitex.entity.Message;

class ViewHolder extends RecyclerView.ViewHolder {

    static final int TYPE_SENT = 1;
    static final int TYPE_RECEIVED = 2;

    private TextView messageTextView;
    private TextView nameTextView;

    ViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.message_item_name);
        messageTextView = itemView.findViewById(R.id.message_item_text);
    }

    void bind(Message message, int messageType) {
        messageTextView.setText(message.getMessage());
        if(messageType == TYPE_RECEIVED) { nameTextView.setText(message.getNickname()); }
    }

}

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final List<Message> messages;
    private final String curUserNickname;

    public MessagesRecyclerAdapter(String curUserNickname, List<Message> messages) {
        this.messages = messages;
        this.curUserNickname = curUserNickname;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.getNickname().equals(curUserNickname)) {
            return ViewHolder.TYPE_SENT;
        } else {
            return ViewHolder.TYPE_RECEIVED;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == ViewHolder.TYPE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_sent, parent, false);
            return new ViewHolder(view);
        } else if (viewType == ViewHolder.TYPE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_received, parent, false);
            return new ViewHolder(view);
        }

        throw new InvalidParameterException("MessagesRecyclerAdapter error: not existing viewType.");

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message, holder.getItemViewType());
    }

    @Override
    public int getItemCount() { return messages.size(); }

}
