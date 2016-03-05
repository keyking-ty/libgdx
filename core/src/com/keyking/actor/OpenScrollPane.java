package com.keyking.actor;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.keyking.data.Node;
import com.keyking.data.TelInfo;
import com.keyking.util.Instances;

public class OpenScrollPane extends ScrollPane implements Instances{
	
	Table lsitTable;
	
	Actor select_s = null;//子节点
	
	Actor select_f = null;//父节点
	
	RefreshLogic sunRefresh;
	
	RefreshLogic fatherRefresh;
	
	public OpenScrollPane() {
		super(new Table(),SKIN.getUISkin());
		lsitTable = (Table)getWidget();
	}
	
	public Actor getSelect_s() {
		return select_s;
	}

	public Actor getSelect_f() {
		return select_f;
	}

	public void setSelect_s(Actor select_s) {
		this.select_s = select_s;
	}

	public void setSelect_f(Actor select_f) {
		this.select_f = select_f;
	}

	public void setSunRefresh(RefreshLogic sunRefresh) {
		this.sunRefresh = sunRefresh;
	}

	public void setFatherRefresh(RefreshLogic fatherRefresh) {
		this.fatherRefresh = fatherRefresh;
	}

	public void refresh(List<Node> nodes){
		lsitTable.reset();
		if (nodes.size() > 0){
			lsitTable.left().top();
			int count = 0;
			for (Node node : nodes) {
				Actor actor = null;
				if (node.couldOpen()){
					actor = UI.createTextButton(node.getName(),"check");
					actor.addListener(new FatherClick());
				}else{
					if (node instanceof TelInfo){
						TelInfo tel = (TelInfo)node;
						actor = UI.createLabel(tel.getName() + "(" + tel.getTelephone() + ")","white");
					}else{
						actor = UI.createLabel(node.getName(),"white");
					}
					actor.addListener(new SunClick());
				}
				actor.setUserObject(node);
				actor.setName(count + node.getFixInfo(0));
	            count += node.getCount();
	            lsitTable.add(actor).left().row();
			}
		}
	}
	
	class SunClick extends ClickListener{
		@Override
		public void clicked (InputEvent event, float x, float y) {
			if (select_s != null){
				((Label)select_s).getStyle().fontColor = SKIN.getUISkin().getColor("white");
			}
			select_s = event.getListenerActor();
			((Label)select_s).getStyle().fontColor = SKIN.getUISkin().getColor("red");
			if (sunRefresh != null){
				sunRefresh.doRefresh();
			}
		}
	}
	
	class FatherClick extends ClickListener{
		@Override
		public void clicked (InputEvent event, float x, float y) {
			if (select_f != null && !select_f.equals(event.getListenerActor())){
				((TextButton)select_f).setChecked(false);
			}
			Node father = (Node)event.getListenerActor().getUserObject();
			select_f = event.getListenerActor();
			if (fatherRefresh != null){
				fatherRefresh.doRefresh();
			}
			String name = select_f.getName();
			String[] ss = name.split(",");
			int index = Integer.parseInt(ss[0]);
    		int len = Integer.parseInt(ss[1]);
    		float space = Float.parseFloat(ss[2]);
    		@SuppressWarnings("rawtypes")
			Array<Cell> cells = lsitTable.getCells();
    		Array<Actor> heads = new Array<Actor>();
    		Array<Actor> tails = new Array<Actor>();
    		Array<Actor> asubs   = new Array<Actor>();
    		for (Cell<?> cell : cells){
    			Actor actor = cell.getActor();
    			String an = actor.getName();
    			String[] ass = an.split(",");
    			int ai = Integer.parseInt(ass[0]);
    			if (ai < index){
    				heads.add(actor);
    			}else if(ai >= index + len){
    				tails.add(actor);
    			}else {
    				asubs.add(actor);
    			}
    		}
    		lsitTable.reset();
			lsitTable.left().top();
			for (Actor actor : heads){
				String an = actor.getName();
    			String[] ass = an.split(",");
    			float aspace = Float.parseFloat(ass[2]);
    			if (aspace > 0){
    				lsitTable.add(actor).left().padLeft(aspace).row();
    			}else{
    				lsitTable.add(actor).left().row();
    			}
			}
			if (space > 0){
				lsitTable.add(select_f).left().padLeft(space).row();
			}else{
				lsitTable.add(select_f).left().row();
			}
			int childCount = index + 1;
			if (((TextButton)select_f).isChecked()){
				for (Node child : father.getChildren()){
					Actor actor = null;
					if (child.couldOpen()){
						actor = UI.createTextButton(child.getName(),"check");
						actor.addListener(new FatherClick());
					}else{
						actor = UI.createLabel(child.getName(),"white");
						actor.addListener(new SunClick());
					}
					actor.setUserObject(child);
					actor.setName(childCount + child.getFixInfo(space + 20));
					childCount += child.getCount();
					lsitTable.add(actor).left().padLeft(space + 20).row();
				}
			}else {
				for (Actor actor : asubs){
					if (actor.equals(select_s)){
						select_s = null;
						if (sunRefresh != null){
							sunRefresh.doRefresh();
						}
						break;
					}
				}
			}
			for (Actor actor : tails){
				String an = actor.getName();
    			String[] ass = an.split(",");
    			float aspace = Float.parseFloat(ass[2]);
    			if (aspace > 0){
    				lsitTable.add(actor).left().padLeft(aspace).row();
    			}else{
    				lsitTable.add(actor).left().row();
    			}
			}
		}
	}
	
	public interface RefreshLogic{
		public void doRefresh();
	}
}
 
