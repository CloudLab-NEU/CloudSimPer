package edu.neu.cloudsimper_simple.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import edu.neu.cloudsimper_simple.Const;
import edu.neu.cloudsimper_simple.meta.MetaContainer;
import edu.neu.cloudsimper_simple.meta.MetaManager;
import edu.neu.cloudsimper_simple.meta.MetaPlugin;

/**
 * @author Peimeng Zhu <pmzhu444@163.com>
 */
public abstract class PluginFactoryAbstract implements PluginFactory {

	protected MetaContainer container;
	protected MetaPlugin metaPlugin;
	protected MetaContainer metaPlug;
	protected String pluginName;
	protected String clazzName;
	protected Class<?> clazz;
	protected Constructor<?> constructor;
	protected Object instance;

	protected PluginFactoryAbstract() {
	}

	public PluginFactory createFacorty(MetaContainer container) {
		clear();
		this.container = container;
		return this;
	}

	protected void clear() {
		this.container = null;
		this.metaPlugin = null;
		this.pluginName = null;
		this.metaPlug = null;
		this.clazzName = null;
		this.clazz = null;
		this.constructor = null;
		this.instance = null;
	}

	protected void prepare(String plugType) {
		this.metaPlugin = container.getPlugin(plugType);
		this.pluginName = metaPlugin == null ? Const.P_DEFAULT : metaPlugin.getName();
		this.metaPlug = MetaManager.getPluginInfor(plugType);
		this.clazzName = metaPlug.getAttribute(pluginName);
		try {
			this.clazz = Class.forName(this.clazzName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void createInstance() {
		try {
			this.instance = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createInstance(Object... initargs) {
		try {
			this.instance = this.constructor.newInstance(initargs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void defineConstructor(Class<?>... paramsCalzz) {
		try {
			this.constructor = clazz.getDeclaredConstructor(paramsCalzz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void setPluginAttribute() {
		if (metaPlugin != null) {
			Method[] methods = this.instance.getClass().getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith("set")) {
					String attrName = methodName.substring(3);
					attrName = (new StringBuilder()).append(Character.toLowerCase(attrName.charAt(0)))
							.append(attrName.substring(1)).toString();
					String value = null;
					if ("name".equals(attrName)) {
						value = metaPlugin.getName();
					} else {
						value = metaPlugin.getAttribute(attrName);
					}
					if (value != null) {
						try {
							Class<?>[] clazz = method.getParameterTypes();
							String type = clazz[0].getName();
							if (type.equals("java.lang.String")) {
								method.invoke(instance, value);
							} else if (type.equals("int") || type.equals("java.lang.Integer")) {
								method.invoke(instance, Integer.parseInt(value));
							} else if (type.equals("double") || type.equals("java.lang.Double")) {
								method.invoke(instance, Double.parseDouble(value));
							} else if (type.equals("float") || type.equals("java.lang.Float")) {
								method.invoke(instance, Float.parseFloat(value));
							} else if (type.equals("boolean") || type.equals("java.lang.Boolean")) {
								method.invoke(instance, Boolean.parseBoolean(value));
							} else if (type.equals("long") || type.equals("java.lang.Long")) {
								method.invoke(instance, Long.parseLong(value));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
