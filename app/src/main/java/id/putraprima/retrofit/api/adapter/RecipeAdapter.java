package id.putraprima.retrofit.api.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    List<Recipe> items;
    Context context;

    public RecipeAdapter(Context context,List<Recipe> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = items.get(position);
        holder.bind(recipe, position);
    }

    @Override
    public int getItemCount() {
        return (items != null) ? items.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtDesc;
        ImageView imgRecipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.rvtxtnama);
            txtDesc = itemView.findViewById(R.id.rvtxtdesc);
            imgRecipe = itemView.findViewById(R.id.imageRecipe);
        }
        public void bind(final Recipe recipe, final int index){
            txtName.setText(recipe.getNama_resep().toString());
            txtDesc.setText(recipe.getDeskripsi().toString());
            String url = "https://mobile.putraprima.id/uploads/"+recipe.getFoto();
            Glide.with(context).load(url).into(imgRecipe);
        }
    }
}
