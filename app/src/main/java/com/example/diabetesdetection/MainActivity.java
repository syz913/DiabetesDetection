package com.example.diabetesdetection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout test_layout;
    private Page the_page;
    //答案列表
    private ArrayList<Answer> the_answer_list;
    //问题列表
    private ArrayList<Quesition> the_quesition_list;
    //问题所在的View
    private View que_view;
    //答案所在的View
    private View ans_view;
    private LayoutInflater xInflater;
    private Page page;
    //下面这两个list是为了实现点击的时候改变图片，因为单选多选时情况不一样，为了方便控制
    //存每个问题下的imageview
    private ArrayList<ArrayList<ImageView>> imglist = new ArrayList<ArrayList<ImageView>>();
    //存每个答案的imageview
    private ArrayList<ImageView> imglist2;

    private boolean answer1 = false, answer2 = false;
    private boolean isAnswer1 = false, isAnswer2 = false;

    private EditText age, waistline, fruit, exercise, sbp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        xInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //假数据
        initDate();

        age = findViewById(R.id.age);
        waistline = findViewById(R.id.Waistline);
        fruit = findViewById(R.id.fruit_frequency);
        exercise = findViewById(R.id.exercise_frequency);
        sbp = findViewById(R.id.SBP);

        //提交按钮
        Button button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new submitOnClickListener(page));
    }

    private void initDate() {
        //假数据
        // TODO Auto-generated method stub

        Answer b_one = new Answer();
        Answer b_two = new Answer();
        Answer b_three = new Answer();
        Answer b_four = new Answer();
        b_one.setAnswerId("0");
        b_one.setAnswer_content("是");
        b_one.setAns_state(0);

        b_two.setAnswerId("1");
        b_two.setAnswer_content("否");
        b_two.setAns_state(0);

        b_three.setAnswerId("2");
        b_three.setAnswer_content("是");
        b_three.setAns_state(0);

        b_four.setAnswerId("3");
        b_four.setAnswer_content("否");
        b_four.setAns_state(0);

        ArrayList<Answer> answers_one = new ArrayList<Answer>();
        answers_one.add(b_one);
        answers_one.add(b_two);

        ArrayList<Answer> answers_two = new ArrayList<Answer>();
        answers_two.add(b_three);
        answers_two.add(b_four);

        Quesition q_one = new Quesition();
        q_one.setQuesitionId("00");
        q_one.setType("0");
        q_one.setContent("您曾经被发现有高血糖吗？");
        q_one.setAnswers(answers_one);
        q_one.setQue_state(0);

        Quesition q_two = new Quesition();
        q_two.setQuesitionId("01");
        q_two.setType("0");
        q_two.setContent("您或您的直系亲属，或其他亲属是否被诊断患有糖尿病？");
        q_two.setAnswers(answers_two);
        q_two.setQue_state(0);

        ArrayList<Quesition> quesitions = new ArrayList<Quesition>();
        quesitions.add(q_one);
        quesitions.add(q_two);

        page = new Page();
        page.setPageId("000");
        page.setStatus("0");
        page.setTitle("测试题");
        page.setQuesitions(quesitions);
        //加载布局
        initView(page);
    }

    private void initView(Page page) {
        // TODO Auto-generated method stub
        //这是要把问题的动态布局加入的布局
        test_layout = (LinearLayout) findViewById(R.id.lly_test);
        TextView page_txt = (TextView) findViewById(R.id.txt_title);
        page_txt.setText(page.getTitle());
        //获得问题即第二层的数据
        the_quesition_list = page.getQuesitions();
        //根据第二层问题的多少，来动态加载布局
        for (int i = 0; i < the_quesition_list.size(); i++) {
            que_view = xInflater.inflate(R.layout.quesition_layout, null);
            TextView txt_que = (TextView) que_view.findViewById(R.id.txt_question_item);
            //这是第三层布局要加入的地方
            LinearLayout add_layout = (LinearLayout) que_view.findViewById(R.id.lly_answer);
            //判断单选-多选来实现后面是*号还是*多选，
            if (the_quesition_list.get(i).getType().equals("1")) {
                set(txt_que, the_quesition_list.get(i).getContent(), 1);
            } else {
                set(txt_que, the_quesition_list.get(i).getContent(), 0);
            }
            //获得答案即第三层数据
            the_answer_list = the_quesition_list.get(i).getAnswers();
            imglist2 = new ArrayList<ImageView>();
            for (int j = 0; j < the_answer_list.size(); j++) {
                ans_view = xInflater.inflate(R.layout.answer_layout, null);
                TextView txt_ans = (TextView) ans_view.findViewById(R.id.txt_answer_item);
                ImageView image = (ImageView) ans_view.findViewById(R.id.image);
                View line_view = ans_view.findViewById(R.id.vw_line);
                if (j == the_answer_list.size() - 1) {
                    //最后一条答案下面不要线是指布局的问题
                    line_view.setVisibility(View.GONE);
                }
                //判断单选多选加载不同选项图片
                if (the_quesition_list.get(i).getType().equals("1")) {
                    image.setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
                } else {
                    image.setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                }
                Log.e("---", "------" + image);
                imglist2.add(image);
                txt_ans.setText(the_answer_list.get(j).getAnswer_content());
                LinearLayout lly_answer_size = (LinearLayout) ans_view.findViewById(R.id.lly_answer_size);
                lly_answer_size.setOnClickListener(new answerItemOnClickListener(i, j, the_answer_list, txt_ans));
                add_layout.addView(ans_view);
            }
			/*for(int r=0; r<imglist2.size();r++){
				Log.e("---", "imglist2--------"+imglist2.get(r));
			}*/

            imglist.add(imglist2);

            test_layout.addView(que_view);
        }
		/*for(int q=0;q<imglist.size();q++){
			for(int w=0;w<imglist.get(q).size();w++){
				Log.e("---", "共有------"+imglist.get(q).get(w));
			}
		}*/

    }

    private void set(TextView tv_test, String content, int type) {
        //为了加载问题后面的* 和*多选
        // TODO Auto-generated method stub
        String w;
        if (type == 1) {
            w = content + "*[多选题]";
        } else {
            w = content + "*";
        }

        int start = content.length();
        int end = w.length();
        Spannable word = new SpannableString(w);
        word.setSpan(new AbsoluteSizeSpan(25), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        word.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv_test.setText(word);
    }

    class answerItemOnClickListener implements View.OnClickListener {
        private int i;
        private int j;
        private TextView txt;
        private ArrayList<Answer> the_answer_lists;

        public answerItemOnClickListener(int i, int j, ArrayList<Answer> the_answer_list, TextView text) {
            this.i = i;
            this.j = j;
            this.the_answer_lists = the_answer_list;
            this.txt = text;

        }

        //实现点击选项后改变选中状态以及对应图片
        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断当前问题是单选还是多选
			/*Log.e("------", "选择了-----第"+i+"题");
			for(int q=0;q<imglist.size();q++){
				for(int w=0;w<imglist.get(q).size();w++){
//					Log.e("---", "共有------"+imglist.get(q).get(w));
				}
			}
			Log.e("----", "点击了---"+imglist.get(i).get(j));*/

            if (the_quesition_list.get(i).getType().equals("1")) {
                //多选
                if (the_answer_lists.get(j).getAns_state() == 0) {
                    //如果未被选中
                    txt.setTextColor(Color.parseColor("#EA5514"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_true));
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                } else {
                    txt.setTextColor(Color.parseColor("#595757"));
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.multiselect_false));
                    the_answer_lists.get(j).setAns_state(0);
                    the_quesition_list.get(i).setQue_state(1);
                }
            } else {
                //单选

                for (int z = 0; z < the_answer_lists.size(); z++) {
                    the_answer_lists.get(z).setAns_state(0);
                    imglist.get(i).get(z).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_false));
                }
                if (the_answer_lists.get(j).getAns_state() == 0) {
                    //如果当前未被选中
                    String id = the_answer_lists.get(j).getAnswerId();
                    if (id.equals("0")) {
                        answer1 = true; isAnswer1 = true;
                    }
                    else if (id.equals("1")) {
                        answer1 = false; isAnswer1 = true;
                    }
                    else if (id.equals("2")){
                        answer2 = true; isAnswer2 = true;
                    }
                    else {
                        answer2 = false; isAnswer2 = true;
                    }
                    System.out.println(answer1 + " , " + answer2);
                    imglist.get(i).get(j).setBackgroundDrawable(getResources().getDrawable(R.drawable.radio_true));
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                } else {
                    //如果当前已被选中
                    System.out.println("false" + the_answer_lists.get(j).getAnswerId());
                    the_answer_lists.get(j).setAns_state(1);
                    the_quesition_list.get(i).setQue_state(1);
                }

            }
            //判断当前选项是否选中
        }
    }

    class submitOnClickListener implements View.OnClickListener {
        private Page page;

        public submitOnClickListener(Page page) {
            this.page = page;
        }

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            //判断是否答完题
            boolean isState = true;
            //最终要的json数组
            //点击提交的时候，先判断状态，如果有未答完的就提示，如果没有再把每条答案提交（包含问卷ID 问题ID 及答案ID）
            //注：不用管是否是一个问题的答案，就以答案的个数为准来提交上述格式的数据
            int ages = Integer.parseInt(age.getText().toString());
            float waist = Float.parseFloat(waistline.getText().toString());
            int fruits = Integer.parseInt(fruit.getText().toString());
            int exercises = Integer.parseInt(exercise.getText().toString());
            float sbps = Float.parseFloat(sbp.getText().toString());
            if(isAnswer1 && isAnswer2 && ages > 0 && waist > 0 && sbps > 0){
                UserData.age = ages;
                UserData.waistline = waist;
                UserData.fruit_frequency = fruits;
                UserData.exercise_frequency = exercises;
                UserData.SBP = sbps;
                UserData.case_history = answer1;
                UserData.family_history = answer2;

                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "请填写完整!!!", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
