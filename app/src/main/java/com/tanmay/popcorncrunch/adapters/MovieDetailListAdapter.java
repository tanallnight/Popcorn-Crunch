package com.tanmay.popcorncrunch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tanmay.popcorncrunch.R;
import com.tanmay.popcorncrunch.models.MovieDetails;

public class MovieDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DIVIDER = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_GENRE = 2;
    private static final int TYPE_INFO_HEADER = 3;
    private static final int TYPE_CAST = 4;

    private MovieDetails movieDetails;

    public MovieDetailListAdapter(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_DIVIDER:
                view = inflater.inflate(R.layout.details_divider, parent, false);
                return new DividerViewHolder(view);
            case TYPE_HEADER:
                view = inflater.inflate(R.layout.details_header, parent, false);
                return new HeaderViewHolder(view);
            case TYPE_GENRE:
                view = inflater.inflate(R.layout.details_genre, parent, false);
                return new GenreViewHolder(view);
            case TYPE_INFO_HEADER:
                view = inflater.inflate(R.layout.details_info_header, parent, false);
                return new InfoHeaderViewHolder(view);
            case TYPE_CAST:
                view = inflater.inflate(R.layout.details_cast, parent, false);
                return new CastViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.tagline.setText(movieDetails.getTagline());
            vh.overview.setText(movieDetails.getOverview());
            vh.popularity.setText("Popularity: " + movieDetails.getPopularity());
            vh.homepage.setText(movieDetails.getHomepage());
        } else if (holder instanceof InfoHeaderViewHolder && position == 2) {
            InfoHeaderViewHolder vh = (InfoHeaderViewHolder) holder;
            vh.infoHeader.setText("Genre");
        } else if (holder instanceof InfoHeaderViewHolder && position == movieDetails.getGenres().size() + 4) {
            InfoHeaderViewHolder vh = (InfoHeaderViewHolder) holder;
            vh.infoHeader.setText("Cast");
        } else if (holder instanceof GenreViewHolder) {
            GenreViewHolder vh = (GenreViewHolder) holder;
            vh.genre.setText(movieDetails.getGenres().get(position - 3).getName());
        } else if (holder instanceof CastViewHolder) {
            CastViewHolder vh = (CastViewHolder) holder;
            vh.name.setText(movieDetails.getCredits().getCast().get(position - movieDetails.getGenres().size() - 5).getName());
            vh.character.setText(movieDetails.getCredits().getCast().get(position - movieDetails.getGenres().size() - 5).getCharacter());
            Picasso.with(vh.profile_path.getContext())
                    .load("http://image.tmdb.org/t/p/w154" + movieDetails.getCredits().getCast().get(position - movieDetails.getGenres().size() - 5).getProfile_path())
                    .placeholder(R.drawable.placeholder)
                    .into(vh.profile_path);
        }
    }

    @Override
    public int getItemCount() {
        return movieDetails.getGenres().size() + movieDetails.getCredits().getCast().size() + 5;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1 || position == movieDetails.getGenres().size() + 3) {
            return TYPE_DIVIDER;
        } else if (position == 2 || position == movieDetails.getGenres().size() + 4) {
            return TYPE_INFO_HEADER;
        } else if (position > 2 && position <= movieDetails.getGenres().size() + 3) {
            return TYPE_GENRE;
        } else {
            return TYPE_CAST;
        }
    }

    public class DividerViewHolder extends RecyclerView.ViewHolder {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView tagline, overview, popularity, homepage;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            tagline = (TextView) itemView.findViewById(R.id.tagline);
            overview = (TextView) itemView.findViewById(R.id.overview);
            popularity = (TextView) itemView.findViewById(R.id.popularity);
            homepage = (TextView) itemView.findViewById(R.id.homepage);

        }
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder {

        TextView genre;

        public GenreViewHolder(View itemView) {
            super(itemView);
            genre = (TextView) itemView.findViewById(R.id.genre);
        }
    }

    public class InfoHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView infoHeader;

        public InfoHeaderViewHolder(View itemView) {
            super(itemView);
            infoHeader = (TextView) itemView.findViewById(R.id.info_header);
        }
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        TextView name, character;
        ImageView profile_path;

        public CastViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            character = (TextView) itemView.findViewById(R.id.character);
            profile_path = (ImageView) itemView.findViewById(R.id.profile_path);

        }
    }
}
