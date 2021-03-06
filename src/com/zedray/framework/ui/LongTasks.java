/*
 * Copyright 2010 Mark Brady
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zedray.framework.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zedray.framework.R;
import com.zedray.framework.utils.Type;

/***
 * Example Activity to demonstrate the framework. Extends the Framework
 * BaseActivity class.
 */
public class LongTasks extends BaseActivity {

    /** UI TextViews. **/
    private TextView mTextView, mTextViewQueue;

    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single);
        setTitle(LongTasks.class.getSimpleName());

        mTextView = (TextView) findViewById(
                R.id.main_TextView_StatusTask);
        mTextViewQueue = (TextView) findViewById(
                R.id.main_TextView_StatusQueue);

        ((Button) findViewById(R.id.main_Button_DoTask)).setOnClickListener(
                new OnClickListener() {
            @Override
            public void onClick(final View view) {
                getServiceQueue().postToService(Type.DO_LONG_TASK, null);
            }
        });
    }

    @Override
    protected final void onResume() {
        updateAll();
        super.onResume();
    }

    /***
     * Update all UI elements - called onCreate() and onResume().
     */
    private void updateAll() {
        updateTextView();
        updateTextViewQueue();
    }

    /***
     * Update the given TextView with information from the Application Cache.
     */
    private void updateTextView() {
        mTextView.setText(getCache().getStateLongTask());
    }


    /***
     * Update the given TextView with information from the Application Cache.
     */
    private void updateTextViewQueue() {
        mTextViewQueue.setText(getCache().getQueue());
    }

    /***
     * Override the post method to receive incoming messages from the Service.
     *
     * @param type Message type.
     * @param bundle Optional Bundle of extra information, NULL otherwise.
     */
    @Override
    public final void post(final Type type, final Bundle bundle) {
        switch (type) {
            case UPDATE_LONG_TASK:
                updateTextView();
                break;

            case UPDATE_QUEUE:
                updateTextViewQueue();
                break;

            default:
                /** Let the BaseActivity handle other message types. */
                super.post(type, bundle);
                break;
        }
    }
}
