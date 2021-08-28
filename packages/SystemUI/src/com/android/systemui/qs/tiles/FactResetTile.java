/*
 * Copyright (C) 2021 Project Radiant
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.content.ComponentName;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;

import javax.inject.Inject;

public class FactResetTile extends QSTileImpl<BooleanState> {

    private final ActivityStarter mActivityStarter;

    @Inject
    public FactResetTile(QSHost host, ActivityStarter activityStarter) {
        super(host);
	    mActivityStarter = activityStarter;
    }

    @Override
    public BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    public void handleClick() {
        refreshState();
    }

    @Override
    protected void handleLongClick() {
	    mHost.collapsePanels();
	    startActivity();
    }

    @Override
    public Intent getLongClickIntent() {
	return null;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_factreset_label);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.NEZUKO;
    }

    @Override
    public void handleSetListening(boolean listening) {
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        state.icon = ResourceIcon.get(R.drawable.ic_del_new);
        state.label = mContext.getString(R.string.quick_settings_factreset_label);
    }

    private void startActivity() {
        Intent nIntent = new Intent(Intent.ACTION_MAIN);
        nIntent.setClassName("com.android.settings",
            "com.android.settings.Settings$FactResActivity");
        mActivityStarter.startActivity(nIntent, true /* dismissShade */);
    }
}