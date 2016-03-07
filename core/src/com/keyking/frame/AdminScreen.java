package com.keyking.frame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.keyking.actor.OpenScrollPane;
import com.keyking.actor.OpenScrollPane.RefreshLogic;
import com.keyking.data.Group;
import com.keyking.data.User;
import com.keyking.util.SkinFactory;

/******
                            _ooOoo_  
                           o8888888o  
                           88" . "88  
                           (| -_- |)  
                            O\ = /O  
                        ____/`---'\____  
                      .' \\| |// `.  
                       / \\||| : |||// \  
                     / _||||| -:- |||||- \  
                       | | \\\ - /// | |  
                     | \_| ''\---/'' | |  
                      \ .-\__ `-` ___/-. /  
                   ___`. .' /--.--\ `. . __  
                ."" '< `.___\_<|>_/___.' >'"".  
               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
                 \ \ `-. \_ __\ /__ _/ .-` / /  
         ======`-.____`-.___\_____/___.-`____.-'======  
                            `=---='  
.............................................................  
                  ���汣��          ����BUG   
          ��Ի:  
                  д��¥��д�ּ䣬д�ּ������Ա��  
                  ������Աд�������ó��򻻾�Ǯ��  
                  ����ֻ�����������������������ߣ�  
                  ���������ո��գ����������긴�ꡣ  
                  ��Ը�������Լ䣬��Ը�Ϲ��ϰ�ǰ��  
                  ���۱�������Ȥ���������г���Ա��  
                  ����Ц��߯��񲣬��Ц�Լ���̫����  
                  ��������Ư���ã��ĸ���ó���Ա?
******/

public class AdminScreen extends GameScreen {
	
	TextButton opration = null;
	
	OpenScrollPane open = null;
	
	Window showWindow = null;//��������
	
	List<CheckBox> checks = new ArrayList<CheckBox>();
	
	boolean selectFalg = false;
	
	TextButton selectAll = null;
	
	@Override
	public boolean init(){
		open = new OpenScrollPane();
		open.setSunRefresh(new RefreshLogic() {
			@Override
			public void doRefresh() {
				refreshWork();
			}
		});
		open.setFatherRefresh(new RefreshLogic(){
			@Override
			public void doRefresh() {
				refreshWork(2);
			}
		});
		float height = SkinFactory.FONT_SIZE * 3;
		open.setSize(S_WIDTH / 3,S_HEIGHT-height);
		open.setPosition(0,height);
		open.refresh(DM.getTreeGroups());
		stage.addActor(open);
		TextButton gadd  = UI.createTextButton("���Ӳ���","check");
		TextButton gred  = UI.createTextButton("ɾ������","check");
		TextButton gfix  = UI.createTextButton("�����޸�","check");
		TextButton uadd  = UI.createTextButton("����Ա��","check");
		TextButton ured  = UI.createTextButton("ɾ��Ա��","check");
		TextButton ufix  = UI.createTextButton("Ա���޸�","check");
		TextButton data  = UI.createTextButton("���ݲ���","check");
		TextButton reset = UI.createTextButton("��������","check");
		float x = 0 ,y = S_HEIGHT - open.getHeight() - SkinFactory.FONT_SIZE - 15;
		float space = (S_WIDTH - gadd.getWidth() * 4)/5;
		x = space;
		gadd.setPosition(x,y);
		gadd.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshGAdd();
			}
		});
		stage.addActor(gadd);
		
		x += gadd.getWidth()+space;
		gred.setPosition(x,y);
		gred.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshGroupDel(false);
			}
		});
		stage.addActor(gred);
		
		x += gred.getWidth()+space;
		gfix.setPosition(x,y);
		gfix.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshGroupFix();
			}
		});
		stage.addActor(gfix);
		
		x += gfix.getWidth()+space;
		uadd.setPosition(x,y);
		uadd.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshUAdd();
			}
		});
		stage.addActor(uadd);
		
		x = space;
		y -= SkinFactory.FONT_SIZE + 15;
		ured.setPosition(x,y);
		ured.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshUserDel(false);
			}
		});
		stage.addActor(ured);
		
		x += ured.getWidth()+space;
		ufix.setPosition(x,y);
		ufix.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshUserFix();
			}
		});
		stage.addActor(ufix);
		
		x += ufix.getWidth()+space;
		data.setPosition(x,y);
		data.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				Dialog tip = new Dialog("ѡ��",SKIN.getUISkin()){
					protected void result (Object object) {
						if (object.equals(true)){
							refreshDataImport();
						}else{
							refreshDataOut();
						}
					}
				};
				Table table = tip.getButtonTable();
				Label label = UI.createLabel("          ѡ���������","red");
				table.add(label).center().row();
				TextButton sure = UI.createTextButton("����");
				table.add(sure).left();
				tip.setObject(sure,true);
				TextButton canle = UI.createTextButton("����");
				table.add(canle).right();
				tip.setObject(canle,false);
				tip.key(Keys.ENTER,true).key(Keys.ESCAPE,false);
				tip.show(stage);
			}
		});
		stage.addActor(data);
		
		x += data.getWidth()+space;
		reset.setPosition(x,y);
		reset.addListener(new CheckSelcetClick(){
			@Override
			public void _clicked(InputEvent event, float x, float y) {
				refreshRest(false);
			}
		});
		stage.addActor(reset);
		
		showWindow = new Window("",SKIN.getUISkin());
		showWindow.setName("9");
		showWindow.setSize(S_WIDTH * 2/3 - 10,S_HEIGHT-height);
		showWindow.setPosition(S_WIDTH/3+5,height);
		stage.addActor(showWindow);
		return true;
	}
	
	public boolean checkOpration(InputEvent event){
		TextButton button = (TextButton)event.getListenerActor();
		if (opration != null){
			if (button.equals(opration)){
				opration.setChecked(true);
				return false;
			}
			opration.setChecked(false);
		}
		opration = button;
		return true;
	}
	
	public void clear(){
		checks.clear();
		showWindow.reset();//������ʾ��
	}
	
	public void refreshWork(int needCode){
		if (showWindow.getName() != null){
			int code = Integer.parseInt(showWindow.getName());
			if (needCode == code){
				refreshWork();
			}
		}
	}
	
	public void refreshWork(){
		if (showWindow.getName() != null){
			int code = Integer.parseInt(showWindow.getName());
			switch(code){
			case 1:
				refreshGAdd();
				break;
			case 2:
				refreshGroupDel(selectFalg);
				break;
			case 3:
				refreshGroupFix();
				break;
			case 4:
				refreshUAdd();
				break;
			case 5:
				refreshUserDel(selectFalg);
				break;
			case 6:
				refreshUserFix();
				break;
			case 7:
				refreshDataImport();
				break;
			case 8:
				refreshRest(selectFalg);
				break;
			}
		}
	}
	
	@Override
	public void logic(float f) {
		clear(1,1,1,1);
	}
	
	public void showCancle(){
		TextButton canle = UI.createTextButton("����");
		canle.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				showWindow.reset();
				showWindow.setName(null);
				checks.clear();
				selectAll = null;
				if (opration != null){
					opration.setChecked(false);
					opration = null;
				}
			}
		});
		canle.setPosition(showWindow.getWidth()-canle.getWidth()-5,0);
		showWindow.addActor(canle);
	}
	
	public void doRest(){
		message("���óɹ�");
		clear();
		refreshRest(false);
	}
	
	public void refreshRest(boolean flag){
		clear();
		showWindow.setName("8");
		List<Group> groups = DM.getGroups();
		if (groups.size() == 0){
			message("û�в��ſɲ���");
			return;
		}
		selectFalg = flag;
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("��������","red");
		float width = showWindow.getWidth();
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		float offset = 0;
		for (Group group : groups){
			CheckBox check = new CheckBox(group.getName(),skin);
			check.setName(group.getId()+"");
			check.addListener(new CheckClick());
			offset += check.getWidth();
			check.setChecked(flag);
			if (offset > width - 150){
				table.add(check).left().row();
				offset = 0;
			}else{
				table.add(check).left();
			}
			checks.add(check);
		}
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		selectAll = UI.createTextButton(flag ? "ȡ��" : "ȫѡ");
		selectAll.setPosition(width/2-30,0);
		selectAll.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				refreshRest(!selectFalg);
			}
		});
		showWindow.addActor(selectAll);
		TextButton sure = UI.createTextButton("ȷ��");
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event,float x,float y){
				int count  = 0;
				for (CheckBox check : checks){
					if (check.isChecked()){
						count ++;
					}
				}
				if (count > 0){
					Dialog tip = new Dialog("",SKIN.getUISkin()){
						protected void result (Object object) {
							if (object.equals(true)){
								//��������
								StringBuffer sb = new StringBuffer();
								for (CheckBox check : checks){
									if (check.isChecked()){
										sb.append(check.getName() + ",");
									}
								}
								sb.deleteCharAt(sb.length()-1);
								NET.reset_g(sb.toString());
							}
						}
					};
					Table table = tip.getButtonTable();
					Label label = UI.createLabel("          ȷ������ѡ��Ĳ�������?","red");
					table.add(label).center().row();
					TextButton sure = UI.createTextButton("ȷ��");
					table.add(sure).left();
					tip.setObject(sure,true);
					TextButton canle = UI.createTextButton("����");
					table.add(canle).right();
					tip.setObject(canle,false);
					tip.key(Keys.ENTER,true).key(Keys.ESCAPE,false);
					tip.show(stage);
				}else{
					message("��ѡ��");
				}
			}
		});
		sure.setPosition(0,0);
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doExport(){
		message("���ݵ����ɹ�");
		if (opration != null){
			opration.setChecked(false);
			opration = null;
		}
		clear();
	}
	
	public void doTelInfoAdd(){
		message("���ݵ���ɹ�");
		if (opration != null){
			opration.setChecked(false);
			opration = null;
		}
		clear();
	}
	
	public void refreshDataOut(){
		clear();
		showWindow.setName("7");
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("���ݵ���","red");
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		table.add("����Ŀ¼:");
		TextField name = UI.createTextField(300,30);
		name.setName("1");
		table.add(name).left();
		TextButton open = UI.createTextButton("���");
		table.add(open).left().row();
		open.setUserObject(name);
		open.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TextField name = (TextField)event.getListenerActor().getUserObject();
				JFileChooser chooser = new JFileChooser("D:\\");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			    	String fileName = chooser.getSelectedFile().getAbsolutePath();
			    	name.setText(fileName);
			    	name.setCursorPosition(fileName.length());
			    }
			}
		});

		table.add("��ʼ����:");
		TextField time = UI.createTextField(300,30);
		time.setName("2");
		table.add(time).left().row();
		table.add("��������:");
		time = UI.createTextField(300,30);
		time.setName("3");
		table.add(time).left().row();
		
		final CheckBox check = new CheckBox("ɾ������",skin,"warning");
		check.setName("4");
		check.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent inputevent, float f, float f1) {
				if (!check.isChecked()){
					return;
				}
				Dialog tip = new Dialog("����",SKIN.getUISkin()){
					protected void result (Object object) {
						if (object.equals(true)){
							check.setChecked(true);
						}else{
							check.setChecked(false);
						}
					}
				};
				Table table = tip.getButtonTable();
				Label label = UI.createLabel("          ȷ��ɾ������?","red");
				table.add(label).center().row();
				TextButton sure = UI.createTextButton("ȷ��");
				table.add(sure).left();
				tip.setObject(sure,true);
				TextButton canle = UI.createTextButton("����");
				table.add(canle).right();
				tip.setObject(canle,false);
				tip.key(Keys.ENTER,true).key(Keys.ESCAPE,false);
				tip.show(stage);
			}
		});
		table.add("   ");
		table.add(check).row();
		
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		TextButton sure = UI.createTextButton("ȷ��");
		sure.setPosition(0,0);
		sure.setUserObject(table);
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Table table = (Table)event.getListenerActor().getUserObject();
				TextField field = searchFromTable(table,1);
				String name = field.getText();
				if (name == null || name.equals("")){
					message("��ѡ�񵼳��ļ�");
					return;
				}
				field = searchFromTable(table,2);
				String time1 = field.getText();
				if (time1 == null || time1.equals("")){
					message("�����뿪ʼ����(��ʽ:2016-05-06)");
					return;
				}
				field = searchFromTable(table,3);
				String time2 = field.getText();
				if (time2 == null || time2.equals("")){
					message("�������������(��ʽ:2016-05-07)");
					return;
				}
				CheckBox check = searchFromTable(table,4);
				NET.exportData(name,time1,time2,check.isChecked());
			}
		});
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void refreshDataImport(){
		clear();
		showWindow.setName("7");
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("���ݵ���","red");
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		table.add("Ŀ¼:").left();
		TextField name = UI.createTextField(300,30);
		name.setName("1");
		table.add(name);
		TextButton open = UI.createTextButton("���");
		table.add(open).row();
		open.setUserObject(name);
		open.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				TextField name = (TextField)event.getListenerActor().getUserObject();
				JFileChooser chooser = new JFileChooser("D:\\");
			    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			    	String fileName = chooser.getSelectedFile().getAbsolutePath();
			    	name.setText(fileName);
			    	name.setCursorPosition(fileName.length());
			    }
			}
		});
		final CheckBox check = new CheckBox("��������",skin,"warning");
		check.setName("2");
		check.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent inputevent, float f, float f1) {
				if (!check.isChecked()){
					return;
				}
				Dialog tip = new Dialog("����",SKIN.getUISkin()){
					protected void result (Object object) {
						if (object.equals(true)){
							check.setChecked(true);
						}else{
							check.setChecked(false);
						}
					}
				};
				Table table = tip.getButtonTable();
				Label label = UI.createLabel("          ȷ����������?","red");
				table.add(label).center().row();
				TextButton sure = UI.createTextButton("ȷ��");
				table.add(sure).left();
				tip.setObject(sure,true);
				TextButton canle = UI.createTextButton("����");
				table.add(canle).right();
				tip.setObject(canle,false);
				tip.key(Keys.ENTER,true).key(Keys.ESCAPE,false);
				tip.show(stage);
			}
		});
		table.add("   ");
		table.add(check).row();
		
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		
		TextButton sure = UI.createTextButton("ȷ��");
		sure.setPosition(0,0);
		sure.setUserObject(table);
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Table table = (Table)event.getListenerActor().getUserObject();
				TextField field = searchFromTable(table,1);
				String name = field.getText();
				if (name == null || name.equals("")){
					message("��ѡ�����ļ�");
					return;
				}
				CheckBox check = searchFromTable(table,2);
				NET.importData(name,check.isChecked());
			}
		});
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doGroupFix(){
		open.refresh(DM.getTreeGroups());
		refreshGroupFix();
		message("�޸ĳɹ�");
	}
	
	public void refreshGroupFix(){
		clear();
		showWindow.setName("3");
		if (open.getSelect_f() == null){
			return;
		}
		String[] ss = open.getSelect_f().getName().split(",");
		int id = Integer.parseInt(ss[3]);
		Group group = (Group)DM.searchGroup(id);
		if (group == null){
			open.setSelect_f(null);
			return;
		}
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("������Ϣ","red");
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		table.add("����:").left().pad(5);
		TextField name = UI.createTextField(group.getName(),skin);
		name.setName("1");
		name.setUserObject(group.getId());
		table.add(name).left().row();
		List<Group> groups = DM.getGroups();
		groups.remove(group);//�ų��Լ�
		table.add("����:").left().pad(5);
		SelectBox<String> select = new SelectBox<String>(skin);
		String[] names = new String[groups.size()+1];
		names[0] = "��";
		String selected = group.getFather() == null ? names[0] : group.getFather().getName();
		if (groups.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 1 ; i < names.length ; i++){
				Group g = groups.get(i-1);
				names[i] = g.getName();
				sb.append(g.getId() + "," + g.getName() + ",");
			}
			sb.deleteCharAt(sb.length()-1);
			select.setUserObject(sb.toString());
		}
		select.setItems(names);
		select.setSelected(selected);
		select.setName("2");
		table.add(select).left().row();
		
		table.add("����:").left().pad(5);
		TextField task = UI.createTextField(group.getTask()+"",skin);
		task.setName("3");
		task.setTextFieldFilter(new NumFieldFieldFilter());
		table.add(task).left().row();
		
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		TextButton sure = UI.createTextButton("ȷ��");
		sure.setPosition(0,0);
		sure.setUserObject(table);
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Table table = (Table)event.getListenerActor().getUserObject();
				TextField field = searchFromTable(table,1);
				String name = field.getText();
				int id = ((Integer)field.getUserObject()).intValue();
				if (name == null || name.equals("")){
					message("�������Ʋ���Ϊ��");
					return;
				}
				SelectBox<String> select = searchFromTable(table,2);
				int fatherId = 0;
				if (select != null){
					String sname = select.getSelected();
					if (!sname.equals("��")){
						String str = select.getUserObject().toString();
						String[] ss = str.split(",");
						for (int i = 0 ;i < ss.length ; i+=2 ){
							if (ss[i+1].equals(sname)){
								fatherId = Integer.parseInt(ss[i]);
								break;
							}
						}
					}
				}
				TextField task = searchFromTable(table,3);
				String str = task.getText();
				int taskNum = 0;
				try{
					taskNum = Integer.parseInt(str);
				}catch(Exception e){
					message("��������ȷ����ֵ");
					return;
				}
				NET.fix_g(id,name,fatherId,taskNum);
			}
		});
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doUserFix(){
		open.refresh(DM.getTreeGroups());
		refreshUserFix();
		message("�޸ĳɹ�");
	}
	
	public void refreshUserFix(){
		clear();
		showWindow.setName("6");
		if (open.getSelect_s() == null){
			return;
		}
		String[] ss = open.getSelect_s().getName().split(",");
		long id = Long.parseLong(ss[3]);
		User user = DM.searchUser(id);
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("Ա����Ϣ","red");
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		table.add("�û�:").left().pad(5);
		TextField name = UI.createTextField(user.getName(),skin);
		name.setName("1");
		name.setUserObject(user.getId());
		table.add(name).left().row();
		
		table.add("����:").left().pad(5);
		TextField pass = UI.createTextField(user.getPass(),skin);
		pass.setName("2");
		table.add(pass).left().row();
		
		List<Group> groups = DM.getGroups();
		table.add("����:").left().pad(5);
		SelectBox<String> select = new SelectBox<String>(skin);
		String[] names = new String[groups.size()+1];
		names[0] = "��";
		String selected = user.getFather() == null ? names[0] : user.getFather().getName();
		if (groups.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 1 ; i < names.length ; i++){
				Group group = groups.get(i-1);
				names[i] = group.getName();
				sb.append(group.getId() + "," + group.getName() + ",");
			}
			sb.deleteCharAt(sb.length()-1);
			select.setUserObject(sb.toString());
		}
		select.setItems(names);
		select.setSelected(selected);
		select.setName("3");
		table.add(select).left().row();
		
		table.add("ְλ:").left().pad(5);
		TextField post = UI.createTextField(user.getPost(),skin);
		post.setName("4");
		table.add(post).left().row();
		
		table.add("����:").left().pad(5);
		TextField task = UI.createTextField(user.getTask() + "",skin);
		task.setName("5");
		task.setTextFieldFilter(new NumFieldFieldFilter());
		table.add(task).left().row();
		
		table.add("����:").left().pad(5);
		TextField first = UI.createTextField(user.getFirstName(),skin);
		first.setName("6");
		table.add(first).left().row();
		
		table.add("����:").left().pad(5);
		TextField last = UI.createTextField(user.getLastName(),skin);
		last.setName("7");
		table.add(last).left().row();
		
		table.add("����:").left().pad(5);
		TextField age = UI.createTextField(user.getAge()+"",skin);
		age.setName("8");
		age.setTextFieldFilter(new NumFieldFieldFilter());
		table.add(age).left().row();
		
		table.add("��ַ:").left().pad(5);
		TextField address = UI.createTextField(user.getAddress(),skin);
		address.setName("9");
		table.add(address).left().row();
		
		table.add("�绰:").left().pad(5);
		TextField telephone = UI.createTextField(user.getTelephone(),skin);
		telephone.setName("10");
		telephone.setTextFieldFilter(new NumFieldFieldFilter());
		table.add(telephone).left().row();
		
		table.add("����:").left().pad(5);
		TextField email = UI.createTextField(user.getEmail(),skin);
		email.setName("11");
		table.add(email).left().row();
		
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		TextButton sure = UI.createTextButton("ȷ��");
		sure.setUserObject(table);
		sure.setPosition(0,0);
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Table table = (Table)event.getListenerActor().getUserObject();
				if (table != null){
					TextField name = searchFromTable(table,1);
					long id  = ((Long)name.getUserObject()).longValue();
					TextField pass = searchFromTable(table,2);
					SelectBox<String> select = searchFromTable(table,3);
					int fatherId = 0;
					if (select != null){
						String sname = select.getSelected();
						if (!sname.equals("��")){
							String str = select.getUserObject().toString();
							String[] ss = str.split(",");
							for (int i = 0 ;i < ss.length ; i+=2 ){
								if (ss[i+1].equals(sname)){
									fatherId = Integer.parseInt(ss[i]);
									break;
								}
							}
						}
					}
					TextField post      = searchFromTable(table,4);
					TextField task      = searchFromTable(table,5);
					TextField first     = searchFromTable(table,6);
					TextField last      = searchFromTable(table,7);
					TextField age       = searchFromTable(table,8);
					TextField address   = searchFromTable(table,9);
					TextField telephone = searchFromTable(table,10);
					TextField email     = searchFromTable(table,11);
					int taskNum = 0 , ageNum = 0;
					try {
						taskNum = Integer.parseInt(task.getText());
					} catch (NumberFormatException e) {
						e.printStackTrace();
						message("��������ȷ����������");
						return;
					}
					try {
						ageNum = Integer.parseInt(age.getText());
					} catch (NumberFormatException e) {
						e.printStackTrace();
						message("��������ȷ����������");
						return;
					}
					User user = new User();
					user.setId(id);
					user.setName(name.getText());
					user.setPass(pass.getText());
					user.setFid(fatherId);
					user.setPost(post.getText());
					user.setTask(taskNum);
					user.setFirstName(first.getText());
					user.setLastName(last.getText());
					user.setAge(ageNum);
					user.setAddress(address.getText());
					user.setTelephone(telephone.getText());
					user.setEmail(email.getText());
					NET.fix_u(user);
				}
			}
		});
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doUserDel(){
		open.refresh(DM.getTreeGroups());
		clear();
		message("ɾ���ɹ�");
	}
	
	public void refreshUserDel(boolean flag){
		clear();
		showWindow.setName("5");
		List<User> users = DM.getUsers();
		if (users.size() == 0){
			message("û�п���ɾ����Ա��");
			return;
		}
		selectFalg = flag;
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("ɾ��Ա��","red");
		showWindow.add(label).center().row();
		float width = showWindow.getWidth();
		Table table = new Table();
		float offset = 0;
		for (User user : users){
			CheckBox check = new CheckBox(user.getName(),skin);
			check.setName(user.getId()+"");
			check.addListener(new CheckClick());
			check.setChecked(flag);
			offset += check.getWidth();
			if (offset > width - 150){
				table.add(check).left().row();
				offset = 0;
			}else{
				table.add(check).left();
			}
			checks.add(check);
		}
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		selectAll = UI.createTextButton(flag ? "ȡ��" : "ȫѡ");
		selectAll.setPosition(width/2-30,0);
		selectAll.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				refreshUserDel(!selectFalg);
			}
		});
		showWindow.addActor(selectAll);
		TextButton sure = UI.createTextButton("ȷ��");
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event,float x,float y){
				int count  = 0;
				for (CheckBox check : checks){
					if (check.isChecked()){
						count ++;
					}
				}
				if (count > 0){
					Dialog tip = new Dialog("",SKIN.getUISkin()){
						protected void result (Object object) {
							if (object.equals(true)){
								StringBuffer sb = new StringBuffer();
								for (CheckBox check : checks){
									if (check.isChecked()){
										sb.append(check.getName() + ",");
									}
								}
								sb.deleteCharAt(sb.length()-1);
								NET.del_u(sb.toString());
							}
						}
					};
					Table table = tip.getButtonTable();
					Label label = UI.createLabel("          ȷ��ɾ��ѡ���Ա��?","red");
					table.add(label).center().row();
					TextButton sure = UI.createTextButton("ȷ��");
					table.add(sure).left();
					tip.setObject(sure,true);
					TextButton canle = UI.createTextButton("����");
					table.add(canle).right();
					tip.setObject(canle,false);
					tip.show(stage);
				}else{
					message("��ѡ��");
				}
			}
		});
		sure.setPosition(0,0);
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doUserAdd(){
		open.refresh(DM.getTreeGroups());
		refreshWork();
		message("���ӳɹ�");
	}
	
	public void refreshUAdd(){
		clear();
		showWindow.setName("4");
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("�����û�","red");
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		List<Group> groups = DM.getGroups();
		table.add("������:").left().pad(10);
		SelectBox<String> select = new SelectBox<String>(skin);
		String[] names = new String[groups.size()+1];
		names[0] = "��";
		if (groups.size() > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 1 ; i < names.length ; i++){
				Group group = groups.get(i-1);
				names[i] = group.getName();
				sb.append(group.getId() + "," + group.getName() + ",");
			}
			sb.deleteCharAt(sb.length()-1);
			select.setUserObject(sb.toString());
		}
		select.setItems(names);
		select.setName("2");
		table.add(select).left().row();
		
		table.add("�û�����:").left().pad(10);
		TextField name = UI.createTextField(SkinFactory.FONT_SIZE * 5,SkinFactory.FONT_SIZE + 10);
		name.setName("1");
		table.add(name).left().row();
		
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		TextButton sure = UI.createTextButton("ȷ��");
		sure.setUserObject(table);
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event,float x,float y){
				Table table = (Table) event.getListenerActor().getUserObject();
				TextField field = searchFromTable(table,1);
				String name = field.getText();
				if (name == null || name.equals("")){
					message("Ա�����Ʋ���Ϊ��");
					return;
				}
				int fatherId = 0;
				SelectBox<String> select = searchFromTable(table,2);
				if (select != null){
					String sname = select.getSelected();
					if (!sname.equals("��")){
						String str = select.getUserObject().toString();
						String[] ss = str.split(",");
						for (int i = 0 ;i < ss.length ; i+=2 ){
							if (ss[i+1].equals(sname)){
								fatherId = Integer.parseInt(ss[i]);
								break;
							}
						}
					}
				}
				NET.add_u(name,fatherId);
			}
		});
		sure.setPosition(0,0);
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doGroupDel(){
		open.refresh(DM.getTreeGroups());
		clear();
		message("ɾ���ɹ�");
	}
	
	public void refreshGroupDel(boolean flag){
		clear();
		showWindow.setName("2");
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("ɾ������","red");
		List<Group> groups = DM.getGroups();
		if (groups.size() == 0){
			message("û�п���ɾ���Ĳ���");
			return;
		}
		selectFalg = flag;
		showWindow.add(label).center().row();
		float width = showWindow.getWidth();
		Table table = new Table();
		float offset = 0;
		for (Group group : groups){
			CheckBox check = new CheckBox(group.getName(),skin);
			check.setName(group.getId()+"");
			check.addListener(new CheckClick());
			offset += check.getWidth();
			check.setChecked(flag);
			if (offset > width - 150){
				table.add(check).left().row();
				offset = 0;
			}else{
				table.add(check).left();
			}
			checks.add(check);
		}
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		selectAll = UI.createTextButton(flag ? "ȡ��" : "ȫѡ");
		selectAll.setPosition(width/2-30,0);
		selectAll.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				refreshGroupDel(!selectFalg);
			}
		});
		showWindow.addActor(selectAll);
		TextButton sure = UI.createTextButton("ȷ��");
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event,float x,float y){
				int count  = 0;
				for (CheckBox check : checks){
					if (check.isChecked()){
						count ++;
					}
				}
				if (count > 0){
					Dialog tip = new Dialog("",SKIN.getUISkin()){
						protected void result (Object object) {
							if (object.equals(true)){
								//��������
								StringBuffer sb = new StringBuffer();
								for (CheckBox check : checks){
									if (check.isChecked()){
										sb.append(check.getName() + ",");
									}
								}
								sb.deleteCharAt(sb.length()-1);
								NET.del_g(sb.toString());
							}
						}
					};
					Table table = tip.getButtonTable();
					Label label = UI.createLabel("          ȷ��ɾ��ѡ��Ĳ���?","red");
					table.add(label).center().row();
					TextButton sure = UI.createTextButton("ȷ��");
					table.add(sure).left();
					tip.setObject(sure,true);
					TextButton canle = UI.createTextButton("����");
					table.add(canle).right();
					tip.setObject(canle,false);
					tip.key(Keys.ENTER,true).key(Keys.ESCAPE,false);
					tip.show(stage);
				}else{
					message("��ѡ��");
				}
			}
		});
		sure.setPosition(0,0);
		showWindow.addActor(sure);
		showCancle();
	}
	
	public void doGroupAdd(){
		open.refresh(DM.getTreeGroups());
		refreshWork();
		message("���ӳɹ�");
	}
	
	class GDelClickListener extends CheckSelcetClick{
		@Override
   	 	public void _clicked (InputEvent event,float x ,float y){
			refreshGroupDel(false);
		}
	}
	
	public void refreshGAdd(){
		clear();
		showWindow.setName("1");
		Skin skin = SKIN.getUISkin();
		Label label = UI.createLabel("��������","red");
		showWindow.add(label).center().row();
		Table table = new Table(skin);
		table.add("��������:").left().pad(10);
		TextField name = UI.createTextField(SkinFactory.FONT_SIZE * 5,SkinFactory.FONT_SIZE + 10);
		name.setName("1");
		table.add(name).left().row();
		List<Group> groups = DM.getGroups();
		if (groups.size() > 0){
			table.add("������:","default-font", "white").left().pad(10);
			SelectBox<String> select = new SelectBox<String>(skin);
			String[] names = new String[groups.size() +1];
			StringBuffer sb = new StringBuffer();
			names[0] = "��";
			for (int i = 1 ; i < names.length ; i++){
				Group group = groups.get(i-1);
				names[i] = group.getName();
				sb.append(group.getId() + "," + group.getName() + ",");
			}
			sb.deleteCharAt(sb.length()-1);
			select.setItems(names);
			select.setName("2");
			select.setUserObject(sb.toString());
			table.add(select).left().row();
		}
		ScrollPane pane = new ScrollPane(table,SKIN.getUISkin());
		showWindow.add(pane).left().row();
		showWindow.add(UI.createLabel("  ")).row();
		TextButton sure = UI.createTextButton("ȷ��");
		sure.setUserObject(table);
		sure.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event,float x,float y){
				Table table = (Table) event.getListenerActor().getUserObject();
				TextField field = searchFromTable(table,1);
				String name = field.getText();
				if (name == null || name.equals("")){
					message("�������Ʋ���Ϊ��");
					return;
				}
				int fatherId = 0;
				SelectBox<String> select = searchFromTable(table,2);
				if (select != null){
					String sname = select.getSelected();
					if (!sname.equals("��")){
						String str = select.getUserObject().toString();
						String[] ss = str.split(",");
						for (int i = 0 ;i < ss.length ; i+=2 ){
							int id = Integer.parseInt(ss[i]);
							if (ss[i+1].equals(sname)){
								fatherId = id;
								break;
							}
						}
					}
				}
				NET.add_g(name,fatherId);
			}
		});
		sure.setPosition(0,0);
		showWindow.addActor(sure);
		showCancle();
	}
	
	class GAddClickListener extends CheckSelcetClick{
		@Override
		public void _clicked(InputEvent event, float x, float y) {
			refreshGAdd();
		}
	}
	
	public abstract class CheckSelcetClick extends ClickListener{
		@Override
   	 	public void clicked (InputEvent event,float x ,float y){
			if (!checkOpration(event)){
				return;
			}
			_clicked(event,x,y);
		}
		public abstract void _clicked(InputEvent event,float x ,float y);
	}
	
	public class CheckClick extends ClickListener{
		@Override
   	 	public void clicked (InputEvent event,float x ,float y){
			if (selectAll == null){
				return;
			}
			int count = 0;
			for (CheckBox check : checks){
				if (check.isChecked()){
					count ++;
				}
			}
			if (count == checks.size()){
				selectAll.setText("ȡ��");
				selectFalg = true;
			}else if (count == 0){
				selectAll.setText("ȫѡ");
				selectFalg = false;
			}
		}
	}
	
	@Override
	public boolean refresh(){
		int code = Integer.parseInt(showWindow.getName());
		if (code == 3 || code == 6){
			if (open.getSelect_f() == null){
				return false;
			}
			String name = code == 3 ? open.getSelect_f().getName() : open.getSelect_s().getName();
			String[] ss = name.split(",");
			int id = Integer.parseInt(ss[3]);
			if (code  == 6){
				id = (int)DM.searchUser(id).getId();
			}
			NET.refresh(code == 3,id);
			return true;
		}
		return false;
	}
}
 
 