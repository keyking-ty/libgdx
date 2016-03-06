package com.keyking.util;

import com.keyking.EngineControler;
import com.keyking.animation.AnimationLoader;
import com.keyking.data.DataManager;
import com.keyking.event.EventFactory;
import com.keyking.net.ServiceUtil;
import com.keyking.ui.UIFactory;

public interface Instances {
	public static final EngineControler ENGINE   = EngineControler.getInstance();
	public static final SkinFactory   SKIN       = SkinFactory.getInstance();
	public static final UIFactory     UI         = UIFactory.getInstance();
	public static final FontData      FONT       = FontData.getInstance();
	public static final EventFactory EVENT       = EventFactory.getInstance();
	public static final AnimationLoader ANI      = AnimationLoader.getInstance();
	public static final ServiceUtil     NET      = ServiceUtil.getInstance();
	public static final DataManager DM           = DataManager.getInstance();
	public static final MathUtil    MATH         = MathUtil.getInstance();
}
 
 
