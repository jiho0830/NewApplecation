package com.example.newapplecation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class adapter2 extends RecyclerView.Adapter<adapter2.CustomHolder> implements Serializable {

    private ArrayList<myreview> marrayList;
    //레이아웃은 화면요소이므로 컨텍스트가 필요하다
    private Context mContext;


    //컨텍스트 메뉴를 사용하려면  RecyclerView.ViewHolder를 상속받는 클래스에서 onCreateContextMenu 저 친구를 구현해야한다
    public class CustomHolder extends RecyclerView.ViewHolder {
        //        protected ImageView imageView; 이미지 구현시
        protected TextView title;
        protected TextView review;
        protected RatingBar ratingBar;
        protected TextView userid;
        protected ImageView imageView;


        public CustomHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.check_textviewname);
            this.review = (TextView) view.findViewById(R.id.check_textviewreview);
            this.userid = (TextView) view.findViewById(R.id.check_userid);
            this.ratingBar = (RatingBar) view.findViewById(R.id.check_ratingstar);
            this.imageView = (ImageView) view.findViewById(R.id.check_imageview);
            // 이 친구를 지금 클래스에서 구현한다는 설정

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.activity_uploadfood3, null, false);
                    builder.setView(view);


                    final TextView editTexttitle = (TextView) view.findViewById(R.id.upload_title3);
                    final TextView editTextreview = (TextView) view.findViewById(R.id.upload_review3);
                    final RatingBar editratingBar = (RatingBar) view.findViewById(R.id.upload_rating3);


                    //원래 있던 데이터들을 불러와서 다이얼로그에 보여줌
                    editTexttitle.setText(marrayList.get(getAdapterPosition()).getTitle());
                    editTextreview.setText(marrayList.get(getAdapterPosition()).getReview());
                    editratingBar.setRating(marrayList.get(getAdapterPosition()).getRatingBar());

                    final TextView score = (TextView) view.findViewById(R.id.score);

                    float rating = editratingBar.getRating();
                    score.setText(rating + "점");


                    final AlertDialog dialog = builder.create();

                    dialog.show();

                }
            });

        }

        //콘텍스트 메뉴를 생성하고 메뉴 항목 선택시 호출되는 리스너를 등록. id로 내가 어떤 메뉴를 선택해서
        //리스너에게 구분 할수 있게 숫자 등록


    }


    public adapter2(Context context, ArrayList<myreview> list) {
        this.marrayList = list;
        this.mContext = context;
    }

    //뷰홀더를 생성(레이아웃생성), 여기에서 생성되는 뷰들은 바인딩을 해줘서 남는다
    @Override
    public CustomHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_listviewreview, viewGroup, false);

        CustomHolder viewHolder = new CustomHolder(view);

        return viewHolder;
    }


    //뷰홀더가 재활용될때 실행되는 메서드, 생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    //새롭게 보여줄 데이터의 인덱스값은 position이라는 이름으로 사용이 되어진다
    @Override
    public void onBindViewHolder(@NonNull CustomHolder viewholder, int position) {


        viewholder.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.review.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.userid.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.ratingBar.setRating(TypedValue.COMPLEX_UNIT_SP);


        viewholder.title.setGravity(Gravity.CENTER);
        viewholder.review.setGravity(Gravity.CENTER);
        viewholder.userid.setGravity(Gravity.CENTER);
        viewholder.ratingBar.setRating(Gravity.CENTER);


        viewholder.title.setText(marrayList.get(position).getTitle());
        viewholder.review.setText(marrayList.get(position).getReview());
        viewholder.userid.setText(marrayList.get(position).getUserid());
        viewholder.ratingBar.setRating(marrayList.get(position).getRatingBar());
//        Bitmap bitmap = BitmapFactory.decodeByteArray(marrayList.get(position).getBytes(), 0, marrayList.get(position).getBytes().length);
////        viewholder.imageView.setImageBitmap(bitmap);

    }

    //아이템의 갯수를 조회,데이터의 전체 길이를 반환해준다
    @Override
    public int getItemCount() {
        return (null != marrayList ? marrayList.size() : 0);
    }

}
