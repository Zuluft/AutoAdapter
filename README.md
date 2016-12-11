# AutoAdapter
This Repository simplifies working with RecyclerView Adapter

## Gradle:
Add it in your root build.gradle at the end of repositories:
```Groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add dependency to app gradle:
```Groovy
compile 'com.github.zuluft:autoadapter:v1.1'
```

## Simple Sample:

### Step 1:
create layout xml file ```item_footballer.xml``` which contains ```TextView``` with id ```tvName``` and ```ImageView``` with id ```ivDelete```

### Step 2 (Optional):
create model, which you want to be drawn on above created layout:
```Java
public class FootballerModel {
    public final String name;
    public final int number;
    public final String team;


    public FootballerModel(String name, int number, String team) {
        this.name = name;
        this.number = number;
        this.team = team;
    }
}
```
### Step 3:
create descendant of ```AutoViewHolder``` with ```ViewHolder``` annotation:

```Java
    @ViewHolder(R.layout.item_footballer)
    public class FootballerViewHolder extends AutoViewHolder {
        public final TextView tvName;

        public FootballerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findViewById(R.id.tvName);
        }
    }
```
by this line 
```Java
 @ViewHolder(R.layout.item_footballer)
``` 
we are telling ```AutoAdapter``` that we want to inflate ```FootballerViewHolder```'s ```itemView``` with ```item_footballer``` layout. 

### Step 4:
create descedent of ```Renderable```  with ```Renderer``` annotation:
(in this example ```FootballerViewHolder``` is the inner static class of the ```FootballerRenderer```)

```Java
@Renderer(FootballerRenderer.FootballerViewHolder.class)
public class FootballerRenderer extends Renderable<FootballerRenderer.FootballerViewHolder> {

    public final FootballerModel footballerModel;

    public String getUsername() {
        return footballerModel.name;
    }

    public FootballerRenderer(FootballerModel footballerModel) {
        this.footballerModel = footballerModel;
    }

    @Override
    public void apply(FootballerViewHolder viewHolder) {
        viewHolder.tvName.setText(footballerModel.name);
    }
    

    @ViewHolder(R.layout.item_footballer)
    public static class FootballerViewHolder extends AutoViewHolder {
        private final TextView tvName;

        public FootballerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findViewById(R.id.tvName);
        }
    }
}
```

by this line 
```Java
 @Renderer(FootballerRenderer.FootballerViewHolder.class)
```
we are telling ```AutoAdapter``` that we want to use instances of ```FootballerViewHolder``` for ```FootballerRenderer``` items.

```Renderable``` Abtsract class has only one abstract method ```apply``` with generic argument type( In our example, this type is ```FootballerViewHolder``` ) and it's being invoked by ```RecyclerView```'s ```onBindViewHolder``` method.

### Step 5:

create an ```Activity```, with the very simple ```RecyclerView``` in it's content view:

create ```FootballerModel``` objects:
```Java
public static FootballerModel[] getUsers() {
        return new FootballerModel[]{
                new FootballerModel("Leo Messi", 10, "Barcelona"),
                new FootballerModel("Andres Iniesta", 8, "Barcelona"),
                new FootballerModel("Neymar Jr.", 11, "Barcelona"),
                new FootballerModel("Ibrahimovic", 9, "Man. United"),
                new FootballerModel("Hazard", 10, "Chelsea"),
                new FootballerModel("Rooney", 10, "Man. United"),
                new FootballerModel("Ozil", 10, "Arsenal"),
                new FootballerModel("Cech", 1, "Chelsea"),
                new FootballerModel("Luis Suarez", 9, "Barcelona"),
        };
    }
```
convert them to ```FootballerRenderer```s:
```Java
public FootballerRenderer[] convertToRenderer(FootballerModel[] footballerModels) {
        FootballerRenderer[] footballerRenderers = new FootballerRenderer[footballerModels.length];
        for (int i = 0; i < footballerModels.length; i++) {
            footballerRenderers[i] = new FootballerRenderer(footballerModels[i]);
        }
        return footballerRenderers;
    }
```
create the ```AutoAdapter``` instance, add ```FootballerRenderer``` objects and set it to ```RecyclerView```
```Java
mRecyclerView = (RecyclerView) findViewById(R.id.rvList);
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
AutoAdapter autoAdapter = new AutoAdapter();
autoAdapter.addAll(convertToRenderer(getUsers()));
mRecyclerView.setAdapter(autoAdapter);
```

if you want to listen item clicks:
```Java
autoAdapter.bindListener(FootballerRenderer.class,
                itemInfo -> toastName(itemInfo.object.getUsername()));
```
or if you want to listen clicks of any ```itemView```s child views, for example view with ```ivDelete``` id:
```Java
autoAdapter.bindListener(FootballerRenderer.class, R.id.ivDelete,
                itemInfo -> autoAdapter.remove(itemInfo.position));
```
this is ```ItemInfo``` class:
```Java
public class ItemInfo<T extends IRenderable, V extends AutoViewHolder> {
    public final int position;
    public final T object;
    public final V viewHolder;

    public ItemInfo(int position, T object, V viewHolder) {
        this.position = position;
        this.object = object;
        this.viewHolder = viewHolder;
    }
}
```
in our example, ```T```=```FootballerRenderer``` and ```V```=```FootballerViewHolder```

# Mmm... If I want to have heterogeneous items and layouts inside ```RecyclerView``` ?
AutoAdapter's working perfectly with heterogeneous items.
You can add any descedent of ```Renderable``` to ```AutoAdapter```, for example it's not a problem to write the following:
```Java
AutoAdapter autoAdapter=new AutoAdapter();
autoAdapter.add(new FootballerRenderer());
autoAdapter.add(new BasketballerRenderer());
autoAdapter.add(new BoxerRenderer());
....
```
They all will draw their own layout. They can use the same ```ViewHolder``` too.


# This is f***ing awesame ! anything else ?
```Auto Adapter``` is using ```SortedList``` and in every descedent of ```Renderable``` we can override these methods. 
here are their default implementations:
```Java
public abstract class Renderable<T extends AutoViewHolder> implements IRenderable<T> {

    @Override
    public int compareTo(IRenderable item) {
        return 0;
    }

    @Override
    public boolean areContentsTheSame(IRenderable item) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(IRenderable item) {
        return false;
    }
}
```
For example, if we want ```FootballRenderers``` to be sorted by ```FootballerModel```s ```number``` property, we can override ```compareTo``` method:
```Java
    @Override
    public int compareTo(IRenderable item) {
        FootballerModel otherFootballer = ((FootballerRenderer) item).footballerModel;
        return Integer.compare(footballerModel.number, otherFootballer.number);
    }
```

