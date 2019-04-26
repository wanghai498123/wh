package cn.com.tass.chsmc.modules.system.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import cn.com.tass.chsmc.modules.common.model.BaseEntity;

/**
 * 标题: 菜单实体类
 * <p>
 * 描述: 菜单实体类
 * <p>
 * 版权: Copyright (c) 2016
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-2-24 下午04:58:13
 * @version 1.0
 */
@Entity
@Table(name = "t_sys_menu")
public class Menu extends BaseEntity{

	private static final long serialVersionUID = 5101225675673387689L;

	
	private int menuID;

	private Menu parentMenu;

	private String menuName;

	private String menuResourceKey;

	private String menuUrl;

	private int status;

	private int menuLevel;

	private int menuOrder;

	private String iconName;
	
	private String menuKey;
	

	
	private List<Menu> childMenuList;
	
	
	private List<Role> role;
	
	
	
	private int isChecked;

	
	@Id
	@GeneratedValue
	public int getMenuID() {
		return menuID;
	}

	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentID")
	@NotFound(action=NotFoundAction.IGNORE)
	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	@OneToMany(fetch=FetchType.EAGER, targetEntity=Menu.class, mappedBy="parentMenu", cascade={javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.REMOVE, javax.persistence.CascadeType.MERGE})
	@OrderBy(value = "menuOrder asc")
	public List<Menu> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<Menu> childMenuList) {
		this.childMenuList = childMenuList;
	}
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuResourceKey() {
		return menuResourceKey;
	}

	public void setMenuResourceKey(String menuResoureKey) {
		this.menuResourceKey = menuResoureKey;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
    
	@ManyToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="list")
	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}
	@Transient
	public int getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(int isChecked) {
		this.isChecked = isChecked;
	}
	
	
	public void setMenuKey(String menuKey){
		this.menuKey=menuKey;
	}
	
	public String getMenuKey(){
		return this.menuKey;
	}
}
