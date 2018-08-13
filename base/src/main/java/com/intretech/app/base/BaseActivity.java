package com.intretech.app.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.intretech.app.base.utils.FileProvider7;
import com.intretech.app.base.utils.StatusBarUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context context = null;
    public BaseFragment currentFragment;
    Activity rootActivity;
    protected  BaseApplication mApplication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取BaseApplication对象
        Activity activity = this;
        while (activity.getParent() != null) {
            activity = activity.getParent();
        }
        mApplication = (BaseApplication) getApplication();
        rootActivity = activity;
        context = this;

    }


    /**
     * 是字体大小等不随系统变化而改变
     * @return Resources
     */
    @Override
    public Resources getResources() {
        // TODO Auto-generated method stub
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics());
        return res;
    }
    public void releaseImageView(ImageView imageView) {
        if (imageView != null) {
            Drawable d = imageView.getDrawable();
            if (d != null) {
                d.setCallback(null);
            }
            imageView.setImageDrawable(null);
            imageView.setBackgroundDrawable(null);
        }
    }

    public void unbindDrawables(View view) {
        if (view == null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public Context getContext() {
        return context;
    }

    /**
     * 绑定界面UI
     **/
    protected abstract void findViewById();

    /**
     * 界面UI事件监听
     **/
    protected abstract void setListener();

    /**
     * 界面UI事件监听
     **/
    protected abstract void reLayout();

    /**
     * 界面数据初始
     **/
    protected abstract void init();

    @TargetApi(19)
    private void setTranslucentStatus(boolean on, Activity mActivity) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {

            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void showShortToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast提示(来自res)
     **/
    protected void showLongToast(int resId) {
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast提示(来自String)
     **/
    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Debug输出Log日志
     **/
    protected void showLogDebug(String tag, String msg) {
        Log.d(tag, msg);
    }

    /**
     * Error输出Log日志
     **/
    protected void showLogError(String tag, String msg) {
        Log.e(tag, msg);
    }

    /**
     * 通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.push_left_in, R.anim.push_left_out);
            ActivityCompat.startActivity(this,
                    intent, compat.toBundle());
        }else{
                    startActivity(intent);
                     overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }

//        startActivity(intent);
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 通过Action跳转界面
     **/
    protected void startActivity(String action) {
        startActivity(action, null);
    }

    /**
     * 含有Bundle通过Action跳转界面
     **/
    protected void startActivity(String action, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(action);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    /**
     * /** 含有标题、内容�?图标、两个按钮的对话�?
     **/
    protected AlertDialog showAlertDialog(String title, String message,
                                          int icon, String positiveText,
                                          DialogInterface.OnClickListener onPositiveClickListener,
                                          String negativeText,
                                          DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setIcon(icon)
                .setPositiveButton(positiveText, onPositiveClickListener)
                .setNegativeButton(negativeText, onNegativeClickListener)
                .show();
        return alertDialog;
    }


    /**
     * 带有右进右出动画的�?�?
     **/
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void defaultFinish() {
        super.finish();
    }

    public void setStatusBarByColor(int color,int uiVisit,int alpha){
        getWindow().getDecorView().setSystemUiVisibility(uiVisit);
        StatusBarUtils.setColor(this, color,alpha);
    }

    /**
     * 关闭指定activity
     * @param exceptActivity
     */
    public void finishActivity(Class<?> exceptActivity){
        super.finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void finishAllExpect(Class<?> exceptActivity){
        mApplication.finishAllExcept( exceptActivity);
    }


    /**
     * 裁剪
     * @param imageUri
     * @return
     */
    protected String  resizeImage(Uri imageUri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        Uri  uri = null;
        File tempFile = getMyPicsFile();
        String tempPath = tempFile.getAbsolutePath();
        uri = FileProvider7.getUriForFile(context,tempFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            List<ResolveInfo> resInfoList = getPackageManager()
                    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 260);
        intent.putExtra("outputY", 260);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("noFaceDetection", true);

        startActivityForResult(intent, GlobalConfig.RequestCode.GOTO_CROP);
        return tempPath;
    }
    /**
     * 调用系统相册
     */
    protected void gotoAlbum(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GlobalConfig.RequestCode.GOTO_IMAGE);
    }

    /**
     * 调用拍照
     * @return
     */
    protected Uri takePictureBySystem(){
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 设置图片输出地址ַ
        Uri getPhotoUri = getMyPicsUri();
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoUri);
        // 低质量
        cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(cameraIntent, GlobalConfig.RequestCode.GOTO_CAMERA);
        return getPhotoUri;
    }

    /*拍照照相*/
    // My Picture输出的图片地址ַ
    protected Uri getMyPicsUri() {
        return FileProvider7.getUriForFile(context,getMyPicsFile());
    }

    // My Picture输出的图片文件
    protected File getMyPicsFile() {
        String currentTime = getCurrentTime("yyyyMMddHHmmss");
        File picFile = createFileByName(GlobalConfig.Path.SYSTEM_PICTURE, GlobalConfig.Path.PIC_PATH,
                GlobalConfig.Path.PREFIX_MONGMEN + currentTime, ".jpg");
        return picFile;
    }
    private File createFileByName(String appPath, String subFolder, String fileName, String suffix) {
		/* 直接将项目名称作为apk文件名 */
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), appPath + File.separator + subFolder + File.separator + fileName + suffix);
        File directory = file.getParentFile();// ??
        if(!directory.exists() && !directory.mkdirs()){
            return file;
        }
        return file;
    }
    public static String getCurrentTime(String timeFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Date dateTime = new Date(System.currentTimeMillis());
        return sdf.format(dateTime);
    }

    /**
     * 绘制一个和状态栏高度的view
     * @param view
     * @param color
     */
    public void drawTop(View view,int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT ){
            int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = this.getResources().getDimensionPixelSize(resourceId);
            // 绘制一个和状态栏一样高的矩形
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            view.setLayoutParams(params);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                view.setBackgroundColor(getResources().getColor(color));
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
           finish();
        }
        return false;
    }


}
