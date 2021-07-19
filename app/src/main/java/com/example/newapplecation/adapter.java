package com.example.newapplecation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.CustomHolder> implements Serializable {

    private ArrayList<myreview> marrayList;
    //레이아웃은 화면요소이므로 컨텍스트가 필요하다
    private Context mContext;


    //컨텍스트 메뉴를 사용하려면  RecyclerView.ViewHolder를 상속받는 클래스에서 onCreateContextMenu 저 친구를 구현해야한다
    public class CustomHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
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
            view.setOnCreateContextMenuListener(this);
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
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem modify = menu.add(Menu.NONE, 1111, 1, "수정");
            MenuItem delete = menu.add(Menu.NONE, 2222, 2, "삭제");
            modify.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        //컨텍스트 메뉴에서 항목클릭시 동작을 설정합니다.
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case 1111: //수정버튼
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_uploadfood2, null, false);
                        builder.setView(view);

                        final Button Buttonupload = (Button) view.findViewById(R.id.upload_uploadbutton);
                        final Button Buttonnoload = (Button) view.findViewById(R.id.upload_noloadbutton);
                        final EditText editTexttitle = (EditText) view.findViewById(R.id.upload_title);
                        final EditText editTextreview = (EditText) view.findViewById(R.id.upload_review);
                        final RatingBar editratingBar = (RatingBar) view.findViewById(R.id.upload_rating);

                        //원래 있던 데이터들을 불러와서 다이얼로그에 보여줌
                        editTexttitle.setText(marrayList.get(getAdapterPosition()).getTitle());
                        editTextreview.setText(marrayList.get(getAdapterPosition()).getReview());
                        editratingBar.setRating(marrayList.get(getAdapterPosition()).getRatingBar());




                        final TextView score = (TextView) view.findViewById(R.id.scorew);

                        float rating = editratingBar.getRating();
                        score.setText(rating + "점");

                        editratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                score.setText(rating + "점");
                            }
                        });


                        final AlertDialog dialog = builder.create();
                        Buttonupload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //수정버튼 클릭시
                                String reviewtitle = editTexttitle.getText().toString();
                                String reviewreview = editTextreview.getText().toString();
                                float reviewrating = editratingBar.getRating();
                                String eduserid = userid.getText().toString();

                                //arraylist(myreview)에 넣고
                                myreview dictionary = new myreview(reviewtitle, reviewreview, reviewrating, eduserid);

                                marrayList.set(getAdapterPosition(), dictionary);

                                Intent intent = new Intent(mContext, Subcheckreview.class);
                                intent.putExtra("modify12", marrayList);
                                System.out.println(marrayList);
                                Log.d("adapter", "어댑터에서 수정시에 값보냄" + marrayList);

                                //어뎁터에서 리사이클러뷰에 반영
                                notifyItemChanged(getAdapterPosition());

                                Subcheckreview subcheckreview12 = (Subcheckreview) Subcheckreview.subcheckreview;
                                subcheckreview12.finish();
                                mContext.startActivity(intent);
                                dialog.dismiss();
                            }
                        });

                        Buttonnoload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;

                    case 2222://삭제버튼시
                        //현재 리스트에서 해당데이터 제거하고
                        marrayList.remove(getAdapterPosition());
                        //어댑터에서 리사이클뷰에 반영
                        //
                        Intent intent = new Intent(mContext, Subcheckreview.class);
                        intent.putExtra("modify12", marrayList);
                        System.out.println(marrayList);
                        Log.d("adapter", "어댑터에서 삭제시에 값보냄");

                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), marrayList.size());

                        Subcheckreview subcheckreview12 = (Subcheckreview) Subcheckreview.subcheckreview;
                        subcheckreview12.finish();
                        mContext.startActivity(intent);

                        break;

                }
                return true;
            }
        };


    }


    public adapter(Context context, ArrayList<myreview> list) {
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

    }

    //아이템의 갯수를 조회,데이터의 전체 길이를 반환해준다
    @Override
    public int getItemCount() {
        return (null != marrayList ? marrayList.size() : 0);
    }

}
