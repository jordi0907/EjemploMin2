package edu.upc.dsa.ejemplominimo2;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.upc.dsa.ejemplominimo2.models.Element;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    List<Element> elementList;
    private static int MODIFY_TRACK = 1;

    public MyAdapter(List<Element> myDataset) {
        elementList = myDataset;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //primera y segunda linea del rowlayout
        public TextView txtAdreca;
        public ImageView imageIcon;
        public View layout;
        // equivalente al oncreate, es un constructor , guardo la vista y recupero los dos elementos del row_layout
        //porque se utiliza recycler, una lista devuelve 5000 por ejemplo lista, el recycler view, genera los que aparece
        //en una vista y el doble en cache, los que desaparecen los reutilizamos y los ponemos alfinal, con 20 ViewHolder los tengo todos
        //No crea 5000 viewHolder , esto ahorra memoria
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtAdreca = (TextView) v.findViewById(R.id.firstLine);
            imageIcon =  v.findViewById(R.id.icon);
        }
    }
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        //false es que no es el raiz
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        final Element element = elementList.get(position);
        final ViewHolder myHolder = holder;

        holder.txtAdreca.setText(elementList.get(position).getAdrecaNom());
        Picasso.get().load(elementList.get(position).getImatge().get(0)).into(holder.imageIcon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MuseumDetailActivity.class);
                String adrecaNomFinal = element.getAdrecaNom();
                String adrecaFinal = element.getGrupAdreca().getAdreca();
                String codiPostalFinal = element.getGrupAdreca().getCodiPostal();
                String escutFinal = element.getRelMunicipis().getMunicipiEscut();
                intent.putExtra("adrecaNom",adrecaNomFinal);
                intent.putExtra("adreca",adrecaFinal);
                intent.putExtra("codiPostal",codiPostalFinal);
                intent.putExtra("escut",escutFinal);

                Activity a = (Activity)holder.itemView.getContext();
                a.startActivityForResult(intent,1);


            }
        });



    }

    @Override
    public int getItemCount() {
        return elementList.size();
    }
}
