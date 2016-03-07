package com.keyking.frame;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.keyking.util.SkinFactory;

public class UserScreen extends GameScreen {
	
	@Override
	public boolean init() {
		showLogo(S_WIDTH/2,S_HEIGHT/2);
		TextButton down    = UI.createTextButton("�����û�");
		down.setWidth(S_WIDTH/2);
		TextButton clear   = UI.createTextButton("���ͨѶ¼");
		clear.setWidth(S_WIDTH/2);
		TextButton commite = UI.createTextButton("�ύ����");
		commite.setWidth(S_WIDTH/2);
		TextButton fix     = UI.createTextButton("�޸�����");
		fix.setWidth(S_WIDTH/2);
		TextButton exit    = UI.createTextButton("�˳�ϵͳ");
		exit.setWidth(S_WIDTH/2);
		
		float space = (S_HEIGHT - down.getHeight() * 5) / 6 + down.getHeight();
		float y = S_HEIGHT - space ;
		float x = S_WIDTH/4;
		down.setPosition(x,y);
		down.addListener(new DownClick());
		stage.addActor(down);
		
		y -= space;
		clear.setPosition(x,y);
		clear.addListener(new ClearClick());
		stage.addActor(clear);
		
		y -= space;
		commite.setPosition(x,y);
		commite.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				refreshCommite();
			}
		});
		stage.addActor(commite);
		
		y -= space;
		fix.setPosition(x,y);
		fix.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				refreshFix();
			}
			
		});
		stage.addActor(fix);
		
		y -= space;
		exit.setPosition(x,y);
		exit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event , float x , float y){
				Exit(0);
			}
		});
		stage.addActor(exit);
		return true;
	}

	@Override
	public void logic(float f) {
		clear(1,1,1,1);
	}
	
	public void doFix(){
		message("�����޸ĳɹ�");
	}
	
	public void refreshFix(){
		
		Dialog fix = new Dialog("�޸�����",SKIN.getUISkin()){
			protected void result (Object object) {
				if (object.equals(true)){
					Table table = getButtonTable();
					TextField old = searchFromTable(table,1);
					if (old == null || old.getText().equals("")){
						message("���������");
						return;
					}
					TextField new1 = searchFromTable(table,2);
					TextField new2 = searchFromTable(table,3);
					if (new1.getText() == null || new1.getText().equals("")){
						message("������������");
						return;
					}
					if (new2.getText() == null || new2.getText().equals("")){
						message("������ȷ������");
						return;
					}
					if (!new1.getText().equals(new2.getText())){
						message("��������������벻����");
						return;
					}
					NET.changePass(old.getText(),new1.getText());
				}
			}
		};
		char pass = "*".charAt(0);
		Table table = fix.getButtonTable();
		Label label = UI.createLabel("�ɵ�����:");
		table.add(label).left();
		TextField field = UI.createTextField(SkinFactory.FONT_SIZE * 20,SkinFactory.FONT_SIZE + 10);
		field.setName("1");
		field.setPasswordMode(true);
		field.setPasswordCharacter(pass);
		table.add(field).left().row();
		label = UI.createLabel("�µ�����:");
		table.add(label).left().padLeft(10);
		field = UI.createTextField(SkinFactory.FONT_SIZE * 20,SkinFactory.FONT_SIZE + 10);
		field.setName("2");
		field.setPasswordMode(true);
		field.setPasswordCharacter(pass);
		table.add(field).left().row();
		label = UI.createLabel("ȷ������:");
		table.add(label).left().padLeft(10);
		field = UI.createTextField(SkinFactory.FONT_SIZE * 20,SkinFactory.FONT_SIZE + 10);
		field.setName("3");
		field.setPasswordMode(true);
		field.setPasswordCharacter(pass);
		table.add(field).left().row();
		
		TextButton sure = UI.createTextButton("ȷ��");
		table.add(sure).left();
		fix.setObject(sure,true);
		TextButton canle = UI.createTextButton("����");
		table.add(canle).right();
		fix.setObject(canle,false);
		fix.show(stage);
	}
	
	public void doCommite(){
		message("�ύ�ɹ�");
	}
	
	public void refreshCommite(){
		Dialog commite = new Dialog("�ύ����",SKIN.getUISkin()){
			protected void result (Object object) {
				if (object.equals(true)){
					TextField field = searchFromTable(getButtonTable(),1);
					String name = field.getText().trim();
					if (name == null || name.equals("")){
						message("������ͻ�����");
						return;
					}
					field = searchFromTable(getButtonTable(),2);
					String num = field.getText().trim();
					if (num == null || num.equals("")){
						message("������ͻ��绰");
						return;
					}
					NET.commite(name + "," +num);
				}
			}
		};
		Table table = commite.getButtonTable();
		Label label = UI.createLabel("�ͻ���:");
		table.add(label).left();
		TextField field = UI.createTextField(SkinFactory.FONT_SIZE * 10,SkinFactory.FONT_SIZE + 10);
		field.setName("1");
		table.add(field).left().row();
		label = UI.createLabel("�绰:");
		table.add(label).left();
		field = UI.createTextField(SkinFactory.FONT_SIZE * 11,SkinFactory.FONT_SIZE + 10);
		field.setName("2");
		field.setTextFieldFilter(new NumFieldFieldFilter());
		table.add(field).left().row();
		TextButton sure = UI.createTextButton("ȷ��");
		table.add(sure).left();
		commite.setObject(sure,true);
		TextButton canle = UI.createTextButton("����");
		table.add(canle).right();
		commite.setObject(canle,false);
		commite.show(stage);
	}
	
	class ClearClick extends ClickListener{
		@Override
		public void clicked(InputEvent event,float x,float y) {
			Dialog exit = new Dialog("����",SKIN.getUISkin()){
				protected void result (Object object) {
					if (object.equals(true)){
						ENGINE.deleteContact();
					}
				}
			};
			Table table = exit.getButtonTable();
			Label label = UI.createLabel("        �Ƿ����ͨѶ¼,�����ѡ��!","red");
			table.add(label).center().row();
			TextButton sure = UI.createTextButton("ȷ��");
			table.add(sure).left();
			exit.setObject(sure,true);
			TextButton canle = UI.createTextButton("����");
			table.add(canle).right();
			exit.setObject(canle,false);
			exit.show(stage);
		}
	}
	
	public void doDown(){
		message("���سɹ�");
	}
	
	class DownClick extends ClickListener{
		@Override
		public void clicked(InputEvent event,float x , float y) {
			Dialog set = new Dialog("����",SKIN.getUISkin()){
				protected void result (Object object) {
					if (object.equals(true)){
						TextField filed = (TextField)getUserObject();
						String str = filed.getText();
						if (str == null || str.equals("")){
							message("��������");
							return;
						}
						NET.down(Integer.parseInt(str));
					}
				}
			};
			Table table = set.getButtonTable();
			Label label = UI.createLabel("ʣ������������" + DM.getTaskNum());
			table.add(label).left().row();
			label = UI.createLabel("����������������");
			table.add(label).left();
			TextField filed = UI.createTextField(DM.getTaskNum() + "",SKIN.getUISkin());
			filed.setTextFieldFilter(new NumFieldFieldFilter());
			table.add(filed).row();
			TextButton sure = UI.createTextButton("ȷ��");
			table.add(sure).left();
			set.setObject(sure,true);
			TextButton canle = UI.createTextButton("����");
			table.add(canle).right();
			set.setObject(canle,false);
			set.setUserObject(filed);
			set.show(stage);
		}
	}
}
 
 