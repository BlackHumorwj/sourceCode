package com.example.source;

import android.os.Bundle;

import com.example.rxjava.RxJavaSource;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // initViewPager();


      // initFragment();

        RxJavaSource.map();

    }


    /**
     * 为什么出现两个 Fragment
     */
    private void initFragment() {

        //返回 BackStackRecord 对象，此对象是一个事务 FragmentTransaction
        final FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        // @NonNull
        //    @Override
        //    public FragmentTransaction beginTransaction() {
        //        return new BackStackRecord(this);
        //    }

        // FragmentTransaction # add()
        final FragmentTransaction fragmentTransaction = beginTransaction.add(R.id.packed, null);

        beginTransaction.replace(0,null);


        //只能在FragmentActivity保存状态前调用 这两个方法，否则报异常
        beginTransaction.commitNow();
        beginTransaction.commit();

        beginTransaction.commitAllowingStateLoss();

       // FragmentTransaction#add()
        //   /**
        //     * Calls {@link #add(int, Fragment, String)} with a null tag.
        //     */
        //    @NonNull
        //    public FragmentTransaction add(@IdRes int containerViewId, @NonNull Fragment fragment) {
        //        doAddOp(containerViewId, fragment, null, OP_ADD);//异步操作的，跟Hander类似
        //        return this;
        //    }


        //FragmentTransaction # doAddOp() 这个方法是怎么进行异步的 ?
        //   void doAddOp(int containerViewId, Fragment fragment, @Nullable String tag, int opcmd) {
        //
        //        final Class<?> fragmentClass = fragment.getClass();
        //
        //        final int modifiers = fragmentClass.getModifiers();
        //
        //        if (fragmentClass.isAnonymousClass() || !Modifier.isPublic(modifiers)
        //                || (fragmentClass.isMemberClass() && !Modifier.isStatic(modifiers))) {
        //            throw new IllegalStateException("Fragment " + fragmentClass.getCanonicalName()
        //                    + " must be a public static class to be  properly recreated from"
        //                    + " instance state.");
        //        }
        //
        //        if (tag != null) {
        //            if (fragment.mTag != null && !tag.equals(fragment.mTag)) {
        //                throw new IllegalStateException("Can't change tag of fragment "
        //                        + fragment + ": was " + fragment.mTag
        //                        + " now " + tag);
        //            }
        //            fragment.mTag = tag;
        //        }
        //
        //        if (containerViewId != 0) {
        //            if (containerViewId == View.NO_ID) {
        //                throw new IllegalArgumentException("Can't add fragment "
        //                        + fragment + " with tag " + tag + " to container view with no id");
        //            }
        //            if (fragment.mFragmentId != 0 && fragment.mFragmentId != containerViewId) {
        //                throw new IllegalStateException("Can't change container ID of fragment "
        //                        + fragment + ": was " + fragment.mFragmentId
        //                        + " now " + containerViewId);
        //            }

        //            fragment.mContainerId = fragment.mFragmentId = containerViewId;
        //        }
        //
        //        addOp(new Op(opcmd, fragment));
        //    }


        //FragmentTransaction # addOp()
        //   void addOp(Op op) {
        //        mOps.add(op);
        //        op.mEnterAnim = mEnterAnim;
        //        op.mExitAnim = mExitAnim;
        //        op.mPopEnterAnim = mPopEnterAnim;
        //        op.mPopExitAnim = mPopExitAnim;
        //    }


        // FragmentTransaction#commit() => BackStackRecord#commit()
        fragmentTransaction.commit();

        //BackStackRecord#commit()
        // @Override
        //    public int commit() {
        //        return commitInternal(false);
        //    }



        // BackStackRecord#commitInternal()
        // int commitInternal(boolean allowStateLoss) {
        //        if (mCommitted) throw new IllegalStateException("commit already called");
        //        if (FragmentManagerImpl.DEBUG) {
        //            Log.v(TAG, "Commit: " + this);
        //            LogWriter logw = new LogWriter(TAG);
        //            PrintWriter pw = new PrintWriter(logw);
        //            dump("  ", pw);
        //            pw.close();
        //        }
        //        mCommitted = true;
        //        if (mAddToBackStack) {
        //            mIndex = mManager.allocBackStackIndex(this);
        //        } else {
        //            mIndex = -1;
        //        }
                  //FragmentManagerImpl#enqueueAction
        //        mManager.enqueueAction(this, allowStateLoss);
        //        return mIndex;
        //    }

        //FragmentManagerImpl#enqueueAction

        //...通过handler post一个任务到消息队列等待执行最后会调用 下面方法

        //FragmentManagerImpl # addFragment()




        //fragment 转换成View 并添加到 Container 中去


        // FragmentTransaction#commit()=>FragmentManagerImpl # moveToState()

        //强制将add 的fragment 的生命周期与Activity 同步
        //    void moveToState(Fragment f, int newState, int transit, int transitionStyle,boolean keepActive) {
        //
        //     // Fragments that are not currently added will sit in the onCreate() state.
        //        if ((!f.mAdded || f.mDetached) && newState > Fragment.CREATED) {
        //            newState = Fragment.CREATED;
        //        }
        //
        //
        //        if (f.mState <= newState) {
        //
        //
        //            switch (f.mState) {
        //                case Fragment.INITIALIZING:
        //                    if (newState > Fragment.INITIALIZING) {
        //                        if (DEBUG) Log.v(TAG, "moveto CREATED: " + f);
        //                        if (f.mSavedFragmentState != null) {
        //                            f.mSavedFragmentState.setClassLoader(mHost.getContext()
        //                                    .getClassLoader());
        //                            f.mSavedViewState = f.mSavedFragmentState.getSparseParcelableArray(
        //                                    FragmentManagerImpl.VIEW_STATE_TAG);
        //                            Fragment target = getFragment(f.mSavedFragmentState,
        //                                    FragmentManagerImpl.TARGET_STATE_TAG);
        //                            f.mTargetWho = target != null ? target.mWho : null;
        //                            if (f.mTargetWho != null) {
        //                                f.mTargetRequestCode = f.mSavedFragmentState.getInt(
        //                                        FragmentManagerImpl.TARGET_REQUEST_CODE_STATE_TAG, 0);
        //                            }
        //                            if (f.mSavedUserVisibleHint != null) {
        //                                f.mUserVisibleHint = f.mSavedUserVisibleHint;
        //                                f.mSavedUserVisibleHint = null;
        //                            } else {
        //                                f.mUserVisibleHint = f.mSavedFragmentState.getBoolean(
        //                                        FragmentManagerImpl.USER_VISIBLE_HINT_TAG, true);
        //                            }
        //                            if (!f.mUserVisibleHint) {
        //                                f.mDeferStart = true;
        //                                if (newState > Fragment.ACTIVITY_CREATED) {
        //                                    newState = Fragment.ACTIVITY_CREATED;
        //                                }
        //                            }
        //                        }
        //
        //                        f.mHost = mHost;
        //                        f.mParentFragment = mParent;
        //                        f.mFragmentManager = mParent != null
        //                                ? mParent.mChildFragmentManager : mHost.mFragmentManager;
        //
        //                        if (f.mTarget != null) {
        //
        //                            if (f.mTarget.mState < Fragment.CREATED) {
        //                                moveToState(f.mTarget, Fragment.CREATED, 0, 0, true);
        //                            }
        //                            f.mTargetWho = f.mTarget.mWho;
        //                            f.mTarget = null;
        //                        }
        //                        if (f.mTargetWho != null) {
        //                            Fragment target = mActive.get(f.mTargetWho);
        //
        //                            if (target.mState < Fragment.CREATED) {
        //                                moveToState(target, Fragment.CREATED, 0, 0, true);
        //                            }
        //                        }
        //
        //                        dispatchOnFragmentPreAttached(f, mHost.getContext(), false);
        //                        f.performAttach();
        //                        if (f.mParentFragment == null) {
                                    //这个方法一调用就会执行Fragment的onAttach(Activity activity)这个生命周期方法
        //                            mHost.onAttachFragment(f);
        //                        } else {
        //                            f.mParentFragment.onAttachFragment(f);
        //                        }
        //                        dispatchOnFragmentAttached(f, mHost.getContext(), false);
        //
        //                        if (!f.mIsCreated) {
        //                            dispatchOnFragmentPreCreated(f, f.mSavedFragmentState, false);
        //
        //                            // 执行生命周期onCreate(savedInstanceState);
        //                            f.performCreate(f.mSavedFragmentState);
        //                            dispatchOnFragmentCreated(f, f.mSavedFragmentState, false);
        //                        } else {
        //                            f.restoreChildFragmentState(f.mSavedFragmentState);
        //                            f.mState = Fragment.CREATED;
        //                        }
        //                    }
        //                    // fall through
        //                case Fragment.CREATED:

        //                    if (newState > Fragment.INITIALIZING) {
        //                        ensureInflatedFragmentView(f);
        //                    }
        //
        //                    if (newState > Fragment.CREATED) {
        //                        if (DEBUG) Log.v(TAG, "moveto ACTIVITY_CREATED: " + f);
        //                        if (!f.mFromLayout) {
        //                            ViewGroup container = null;
        //                            if (f.mContainerId != 0) {
        //                               //从activity中找到我们需要存放Fragment的ViewGroup布局
        //                                container = (ViewGroup) mContainer.onFindViewById(f.mContainerId);
        //                                if (container == null && !f.mRestored) {
        //                                    String resName;
        //                                    try {
        //                                        resName = f.getResources().getResourceName(f.mContainerId);
        //                                    } catch (Resources.NotFoundException e) {
        //                                        resName = "unknown";
        //                                    }
        //                                    throwException(new IllegalArgumentException(
        //                                            "No view found for id 0x"
        //                                                    + Integer.toHexString(f.mContainerId) + " ("
        //                                                    + resName
        //                                                    + ") for fragment " + f));
        //                                }
        //                            }
        //                            f.mContainer = container;
        //
        //                            f.performCreateView(f.performGetLayoutInflater(f.mSavedFragmentState), container, f.mSavedFragmentState);
        //                            if (f.mView != null) {
        //                                f.mInnerView = f.mView;
        //                                f.mView.setSaveFromParentEnabled(false);
        //                                if (container != null) {
        //
        //                             // 如果ViewGroup不等于null就把从onCreateView()生命周期中获得的View添加到该布局中
        //                            // 最主要的就是这个方法，其实我们可以把Fragment理解成一个自定义的类
        //                            // 通过onCreateView()获取的到View添加到一个FragmentActivity的一个ViewGroup中
        //                            // 只不过它有自己的生命周期而已......
        //                                    container.addView(f.mView);
        //                                }
        //                                if (f.mHidden) {
        //                                    f.mView.setVisibility(View.GONE);
        //                                }
        //                                f.onViewCreated(f.mView, f.mSavedFragmentState);
        //                                dispatchOnFragmentViewCreated(f, f.mView, f.mSavedFragmentState,
        //                                        false);
        //                                // Only animate the view if it is visible. This is done after
        //                                // dispatchOnFragmentViewCreated in case visibility is changed
        //                                f.mIsNewlyAdded = (f.mView.getVisibility() == View.VISIBLE)&& f.mContainer != null;
        //                            } else {
        //                                f.mInnerView = null;
        //                            }
        //                        }
        //
        //                        f.performActivityCreated(f.mSavedFragmentState);
        //                        dispatchOnFragmentActivityCreated(f, f.mSavedFragmentState, false);
        //                        if (f.mView != null) {
        //                            f.restoreViewState(f.mSavedFragmentState);
        //                        }
        //                        f.mSavedFragmentState = null;
        //                    }
        //                    // fall through
        //                case Fragment.ACTIVITY_CREATED:
        //                    if (newState > Fragment.ACTIVITY_CREATED) {
        //                        if (DEBUG) Log.v(TAG, "moveto STARTED: " + f);
        //                        f.performStart();
        //                        dispatchOnFragmentStarted(f, false);
        //                    }
        //                    // fall through
        //                case Fragment.STARTED:
        //                    if (newState > Fragment.STARTED) {
        //                        if (DEBUG) Log.v(TAG, "moveto RESUMED: " + f);
        //                        f.performResume();
        //                        dispatchOnFragmentResumed(f, false);
        //                        f.mSavedFragmentState = null;
        //                        f.mSavedViewState = null;
        //                    }
        //            }
        //        }
        //    }


        // replace

        //FragmentActivity 异常情况下保存流程
       // FragmentManagerImpl # saveAllState



       //FragmentActivity 恢复流程

        //FragmentManagerImpl # restoreSaveState()

        //  void restoreSaveState(Parcelable state) {
        //        // If there is no saved state at all, then there's nothing else to do
        //        if (state == null) return;
        //        FragmentManagerState fms = (FragmentManagerState)state;
        //        if (fms.mActive == null) return;
        //
        //        // Build the full list of active fragments, instantiating them from
        //        // their saved state.
        //        mActive.clear();
        //        for (FragmentState fs : fms.mActive) {
        //            if (fs != null) {
                           //FragmentState#instantiate() 创建Fragment
        //                Fragment f = fs.instantiate(mHost.getContext().getClassLoader(),getFragmentFactory());
        //                f.mFragmentManager = this;
        //                if (DEBUG) Log.v(TAG, "restoreSaveState: active (" + f.mWho + "): " + f);
        //                mActive.put(f.mWho, f);

        //                fs.mInstance = null;
        //            }
        //        }
        //
        //        // Build the list of currently added fragments.
        //        mAdded.clear();
        //        if (fms.mAdded != null) {
        //            for (String who : fms.mAdded) {
        //                Fragment f = mActive.get(who);
        //                if (f == null) {
        //                    throwException(new IllegalStateException(
        //                            "No instantiated fragment for (" + who + ")"));
        //                }
        //                f.mAdded = true;
        //                if (mAdded.contains(f)) {
        //                    throw new IllegalStateException("Already added " + f);
        //                }
        //                synchronized (mAdded) {
        //                    mAdded.add(f);
        //                }
        //            }
        //        }
        //
        //        // Build the back stack.
        //        if (fms.mBackStack != null) {
        //            mBackStack = new ArrayList<BackStackRecord>(fms.mBackStack.length);
        //            for (int i=0; i<fms.mBackStack.length; i++) {
        //                BackStackRecord bse = fms.mBackStack[i].instantiate(this);
        //
        //                mBackStack.add(bse);
        //                if (bse.mIndex >= 0) {
        //                    setBackStackIndex(bse.mIndex, bse);
        //                }
        //            }
        //        } else {
        //            mBackStack = null;
        //        }
        //
        //        if (fms.mPrimaryNavActiveWho != null) {
        //            mPrimaryNav = mActive.get(fms.mPrimaryNavActiveWho);
        //            dispatchParentPrimaryNavigationFragmentChanged(mPrimaryNav);
        //        }
        //        this.mNextFragmentIndex = fms.mNextFragmentIndex;
        //    }


        // FragmentActivity #  onStart() 恢复当前的状态显示

        //  /**
        //     * Dispatch onStart() to all fragments.
        //     */
        //    @Override
        //    protected void onStart() {
        //        super.onStart();
        //
        //        mStopped = false;
        //
        //        if (!mCreated) {
        //            mCreated = true;
        //            mFragments.dispatchActivityCreated();
        //        }
        //
        //        mFragments.noteStateNotSaved();
        //        mFragments.execPendingActions();
        //
        //        // NOTE: HC onStart goes here.
        //
        //        mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        //        mFragments.dispatchStart();
        //    }


    }


    /**
     * ViewPager应对后台杀死做的兼容
     */
    private void initViewPager() {
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return DemoFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        //FragmentPagerAdapter # instantiateItem()

        // @SuppressWarnings({"ReferenceEquality", "deprecation"})
        //    @NonNull
        //    @Override
        //    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //        if (mCurTransaction == null) {
        //            mCurTransaction = mFragmentManager.beginTransaction();
        //        }
        //
        //        final long itemId = getItemId(position);
        //
        //        // Do we already have this fragment?
        //        // 是否已经有Fragment了
        //        String name = makeFragmentName(container.getId(), itemId);
        //        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        //
        //        if (fragment != null) {
                  //如果已经有了就不会通过 直接attach()
        //            if (DEBUG) Log.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
        //            mCurTransaction.attach(fragment);
        //        } else {
                  //否则 通过 getItem 拿到Fragment
        //            fragment = getItem(position);
        //            if (DEBUG) Log.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
        //            mCurTransaction.add(container.getId(), fragment,
        //                    makeFragmentName(container.getId(), itemId));
        //        }
        //        if (fragment != mCurrentPrimaryItem) {
        //            fragment.setMenuVisibility(false);
        //            if (mBehavior == BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        //                mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.STARTED);
        //            } else {
        //                fragment.setUserVisibleHint(false);
        //            }
        //        }
        //
        //        return fragment;
        //    }



    }


}
