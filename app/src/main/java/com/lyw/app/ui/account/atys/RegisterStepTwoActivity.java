package com.lyw.app.ui.account.atys;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyw.app.R;
import com.lyw.app.ui.account.bean.PhoneToken;
import com.lyw.app.utils.TDevice;


/**
 * Created by fei
 * on 2016/10/14.
 * desc:
 */

public class RegisterStepTwoActivity extends AccountBaseActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final String PHONE_TOKEN_KEY = "phoneToken";

    private LinearLayout mLlRegisterBar;
    private LinearLayout mLltwo_container;

    private LinearLayout mLlRegisterTwoUsername;
    private EditText mEtRegisterUsername;
    private ImageView mIvRegisterUsernameDel;
    private LinearLayout mLlRegisterTwoPwd;
    private EditText mEtRegisterPwd;
    private TextView mTvRegisterMan;
    private TextView mTvRegisterFemale;
    private Button mBtRegisterSubmit;

    private PhoneToken mPhoneToken;

    /*private TextHttpResponseHandler mHandler = new TextHttpResponseHandler() {


        @Override
        public void onStart() {
            super.onStart();
            showWaitDialog(R.string.progress_submit);
        }

        @Override
        public void onFinish() {
            super.onFinish();
            hideWaitDialog();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            requestFailureHint(throwable);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            Type type = new TypeToken<ResultBean<User>>() {
            }.getType();
            ResultBean<User> resultBean = AppOperator.createGson().fromJson(responseString, type);

            if (resultBean.isSuccess()) {
                User user = resultBean.getResult();
                if (AccountHelper.login(user, headers)) {
                    AppContext.showToast(getResources().getString(R.string.register_success_hint), Toast.LENGTH_SHORT);
                    sendLocalReceiver();
                    finish();
                } else {
                    showToastForKeyBord("注册异常");
                }
            } else {
                int code = resultBean.getCode();
                switch (code) {
                    case 216:
                        //phoneToken 已经失效
                        finish();
                        break;
                    case 217:
                        mLlRegisterTwoUsername.setBackgroundResource(R.drawable.bg_login_input_error);
                        break;
                    case 218:
                        finish();
                        break;
                    case 219:
                        mLlRegisterTwoPwd.setBackgroundResource(R.drawable.bg_login_input_error);
                        break;
                    default:
                        break;
                }
                showToastForKeyBord(resultBean.getMessage());
            }

        }
    };*/
    private int mTopMargin;

    /**
     * show register step two activity
     *
     * @param context context
     */
    public static void show(Context context, PhoneToken phoneToken) {
        Intent intent = new Intent(context, RegisterStepTwoActivity.class);
        intent.putExtra(PHONE_TOKEN_KEY, phoneToken);
        context.startActivity(intent);
    }
//测试注册界面2
    public static void show(Context context) {
        Intent intent = new Intent(context, RegisterStepTwoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main_register_step_two;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initViews();
        setListener();
        TextView tvLabel = (TextView) mLlRegisterBar.findViewById(R.id.tv_navigation_label);
        tvLabel.setText(R.string.login_register_hint);

        mEtRegisterUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();

                String smsCode = mEtRegisterPwd.getText().toString().trim();

                if (!TextUtils.isEmpty(smsCode)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

                if (length > 0) {
                    mIvRegisterUsernameDel.setVisibility(View.VISIBLE);
                } else {
                    mIvRegisterUsernameDel.setVisibility(View.INVISIBLE);
                }

                if (length > 12) {
                    showToastForKeyBord(R.string.register_username_error);
                    mLlRegisterTwoUsername.setBackgroundResource(R.drawable.bg_login_input_error);
                } else {
                    mLlRegisterTwoUsername.setBackgroundResource(R.drawable.bg_login_input_ok);
                }
            }
        });
        mEtRegisterUsername.setOnFocusChangeListener(this);
        mEtRegisterPwd.setOnFocusChangeListener(this);
        mEtRegisterPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length < 6) {
                    mLlRegisterTwoPwd.setBackgroundResource(R.drawable.bg_login_input_error);
                } else {
                    mLlRegisterTwoPwd.setBackgroundResource(R.drawable.bg_login_input_ok);
                }
                String username = mEtRegisterUsername.getText().toString().trim();
                if (!TextUtils.isEmpty(username)) {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtRegisterSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    mBtRegisterSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
            }
        });
    }

    private void initViews() {
        mLlRegisterBar = (LinearLayout) findViewById(R.id.ly_register_bar);
        mLltwo_container= (LinearLayout) findViewById(R.id.lay_register_two_container);
        mLlRegisterTwoUsername= (LinearLayout) findViewById(R.id.ll_register_two_username);
        mEtRegisterUsername= (EditText) findViewById(R.id.et_register_username);
        mIvRegisterUsernameDel= (ImageView) findViewById(R.id.iv_register_username_del);
        mLlRegisterTwoPwd= (LinearLayout) findViewById(R.id.ll_register_two_pwd);
        mEtRegisterPwd= (EditText) findViewById(R.id.et_register_pwd_input);
        mTvRegisterMan= (TextView) findViewById(R.id.tv_register_man);
        mTvRegisterFemale= (TextView) findViewById(R.id.tv_register_female);
        mBtRegisterSubmit= (Button) findViewById(R.id.bt_register_submit);
    }

    @Override
    protected void initData() {
        super.initData();//必须要调用,用来注册本地广播
        Intent intent = getIntent();
        mPhoneToken = (PhoneToken) intent.getSerializableExtra(PHONE_TOKEN_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLlRegisterBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideKeyBoard(getCurrentFocus().getWindowToken());
        mLlRegisterBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})

    private void setListener(){
        mLlRegisterBar.setOnClickListener(this);
        mIvRegisterUsernameDel.setOnClickListener(this);
        mTvRegisterMan.setOnClickListener(this);
        mTvRegisterFemale.setOnClickListener(this);
        mBtRegisterSubmit.setOnClickListener(this);
        mLltwo_container.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.iv_register_username_del:
                mEtRegisterUsername.setText(null);
                break;
            case R.id.tv_register_man:
                if (mTvRegisterMan.getTag() != null) {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_male_normal);
                    mTvRegisterMan.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterMan.setTag(null);
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_male_actived);
                    mTvRegisterMan.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterMan.setTag(false);
                    Drawable female = getResources().getDrawable(R.mipmap.btn_gender_female_normal);
                    mTvRegisterFemale.setCompoundDrawablesWithIntrinsicBounds(female, null, null, null);
                    mTvRegisterFemale.setTag(null);
                }

                break;
            case R.id.tv_register_female:
                if (mTvRegisterFemale.getTag() != null) {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_female_normal);
                    mTvRegisterFemale.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterFemale.setTag(null);
                } else {
                    Drawable left = getResources().getDrawable(R.mipmap.btn_gender_female_actived);
                    mTvRegisterFemale.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    mTvRegisterFemale.setTag(true);

                    Drawable men = getResources().getDrawable(R.mipmap.btn_gender_male_normal);
                    mTvRegisterMan.setCompoundDrawablesWithIntrinsicBounds(men, null, null, null);
                    mTvRegisterMan.setTag(null);
                }
                break;
            case R.id.bt_register_submit:
                requestRegisterUserInfo();
                break;
            case R.id.lay_register_two_container:
                hideKeyBoard(getCurrentFocus().getWindowToken());
                break;
            default:
                break;
        }

    }

    private void requestRegisterUserInfo() {

        String username = mEtRegisterUsername.getText().toString().trim();
        String pwd = mEtRegisterPwd.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            return;
        }

        if (!TDevice.hasInternet()) {
            showToastForKeyBord(R.string.tip_network_error);
            return;
        }

        int gender = 0;

        Object isMan = mTvRegisterMan.getTag();
        if (isMan != null) {
            gender = 1;
        }

        Object isFemale = mTvRegisterFemale.getTag();
        if (isFemale != null) {
            gender = 2;
        }

       //111 OSChinaApi.register(username, getSha1(pwd), gender, mPhoneToken.getToken(), mHandler);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        int id = v.getId();
        switch (id) {
            case R.id.et_register_username:
                if (hasFocus) {
                    mLlRegisterTwoUsername.setActivated(true);
                    mLlRegisterTwoPwd.setActivated(false);
                }
                break;
            case R.id.et_register_pwd_input:
                if (hasFocus) {
                    mLlRegisterTwoPwd.setActivated(true);
                    mLlRegisterTwoUsername.setActivated(false);
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onGlobalLayout() {

        final LinearLayout layRegisterTwoUsername = this.mLlRegisterTwoUsername;
        Rect keypadRect = new Rect();

        mLlRegisterBar.getWindowVisibleDisplayFrame(keypadRect);

        int screenHeight = mLlRegisterBar.getRootView().getHeight();
        int keypadHeight = screenHeight - keypadRect.bottom;

        if (keypadHeight > 0) {
            updateKeyBoardActiveStatus(true);
        } else {
            updateKeyBoardActiveStatus(false);
        }

        if (keypadHeight > 0 && layRegisterTwoUsername.getTag() == null) {
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layRegisterTwoUsername.getLayoutParams();
            final int topMargin = layoutParams.topMargin;
            this.mTopMargin = topMargin;
            layRegisterTwoUsername.setTag(true);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    layRegisterTwoUsername.requestLayout();
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();

        } else if (keypadHeight == 0 && layRegisterTwoUsername.getTag() != null) {
            final int topMargin = mTopMargin;
            final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layRegisterTwoUsername.getLayoutParams();
            layRegisterTwoUsername.setTag(null);
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    layoutParams.topMargin = (int) (topMargin * animatedValue);
                    layRegisterTwoUsername.requestLayout();
                }
            });
            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();

        }
    }
}
