package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        try {
            var configInstance = configClass.getConstructor().newInstance();
            var methods = Arrays.stream(configClass.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(AppComponent.class))
                    .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                    .toList();

            for (Method method : methods) {
                try {
                    String name = method.getAnnotation(AppComponent.class).name();
                    var parameters = Arrays.stream(method.getParameterTypes()).map(this::getAppComponent).toList();
                    var bean = method.invoke(configInstance, parameters.toArray());

                    if (appComponentsByName.containsKey(name)) {
                        throw new RuntimeException("Constraint unique bean");
                    }

                    appComponentsByName.put(name, bean);
                    appComponents.add(bean);
                } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var count = appComponents.stream()
                .filter(component -> Arrays.stream(component.getClass().getInterfaces())
                        .allMatch(component1 -> component1 == componentClass))
                .toList().size();

        if (count > 1) {
            throw new RuntimeException(String.format("Found more than one bean - %s", componentClass.getSimpleName()));
        }

        for (Object component : appComponents) {
            if (Arrays.stream(component.getClass().getInterfaces()).anyMatch(item -> item == componentClass)) {
                return (C) component;
            }
            if (component.getClass() == componentClass) {
                return (C) component;
            }
        }

        throw new RuntimeException("Component not found");
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var name = (C) appComponentsByName.get(componentName);
        if (Objects.isNull(name)) {
            throw new RuntimeException(String.format("Component with name %s was not found", componentName));
        }
        return name;
    }
}
