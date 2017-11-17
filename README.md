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
implementation 'com.github.Zuluft:AutoAdapter:v2.3.1'
annotationProcessor 'com.github.Zuluft:AutoAdapter:v2.3.1'
```

## Simple Sample:

### Step 1:

create layout xml file, for example ```item_footballer.xml``` which contains ```TextView```s with ids ```tvName```, ```tvNumber```, ```tvClub``` and ```ImageView``` with id ```ivDelete```

### Step 2 (Optional):

create model, which you want to be drawn on above created layout:
```Java
public final class FootballerModel {
    private final String name;
    private final int number;
    private final String club;

    public FootballerModel(final String name,
                           final int number,
                           final String club) {
        this.name = name;
        this.number = number;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getClub() {
        return club;
    }
}
```
### Step 3:

create 'Renderer' class by the following way:

```Java
   @Render(layout = R.layout.item_footballer,
        views = {
                @ViewField(
                        id = R.id.tvName,
                        name = "tvName",
                        type = TextView.class
                ),
                @ViewField(
                        id = R.id.tvNumber,
                        name = "tvNumber",
                        type = TextView.class
                ),
                @ViewField(
                        id = R.id.tvClub,
                        name = "tvClub",
                        type = TextView.class
                )
        })
public class FootballerRenderer{
        
}
```
```@Render``` annotation is needed to generate ```ViewHolder``` for this ```Renderer``` by annotation processor.
Inside ```@Render``` annotation ```layout``` value is an itemView layout id and ```@ViewField```s are informations about the views in this layout. Name of the generated ```ViewHolder``` will be <RendererClassName>ViewHolder, in this case ```FootballerRendererViewHolder```

### Step 4:

Rebuild the project

### Step 5:

Extend your ```FootballerRenderer``` by ```Renderer``` and pass newly generated ```FootballerRendererViewHolder``` as a generic type:

```Java
@Render(layout = R.layout.item_footballer,
        views = {
                @ViewField(
                        id = R.id.tvName,
                        name = "tvName",
                        type = TextView.class
                ),
                @ViewField(
                        id = R.id.tvNumber,
                        name = "tvNumber",
                        type = TextView.class
                ),
                @ViewField(
                        id = R.id.tvClub,
                        name = "tvClub",
                        type = TextView.class
                )
        })
public class FootballerRenderer
        extends
        Renderer<FootballerRendererViewHolder> {

    public final FootballerModel footballerModel;

    public FootballerRenderer(final FootballerModel footballerModel) {
        this.footballerModel = footballerModel;
    }

    @Override
    public void apply(@NonNull final FootballerRendererViewHolder vh) {
        final Context context = vh.getContext();
        vh.tvName.setText(footballerModel.getName());
        vh.tvClub.setText(footballerModel.getClub());
        vh.tvNumber.setText(context.getString(R.string.footballer_number_template,
                footballerModel.getNumber()));
    }
}
```

As you see ```FootballerRendererViewHolder``` has ```tvName```, ```tvClub```, ```tvNumber``` fields.

### Step 6:

```Java
  ...
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
	...
	mAutoAdapter = AutoAdapterFactory.createAutoAdapter();
        mAutoAdapter.addAll(Stream.of(getFootballers()).map(FootballerRenderer::new)
                .collect(Collectors.toList()));
        mRecyclerView.setAdapter(mAutoAdapter);
    }


    private List<FootballerModel> getFootballers() {
        return Arrays.asList(
                new FootballerModel("Luis Suarez", 9, "Barcelona"),
                new FootballerModel("Leo Messi", 10, "Barcelona"),
                new FootballerModel("Ousmane Dembele", 11, "FC Barcelona"),
                new FootballerModel("Harry Kane", 9, "Tottenham Hotspur"),
                new FootballerModel("Dele Alli", 20, "Tottenham Hotspur"),
                new FootballerModel("Alexis Sanchez", 7, "Arsenal")
        );
    }
```
This line ```Stream.of(getFootballers()).map(FootballerRenderer::new).collect(Collectors.toList())``` converts ```FootballerModel``` to
```FootballerRenderer``` using [Stream](https://github.com/aNNiMON/Lightweight-Stream-API) 

# Mmm... If I want to have heterogeneous items and layouts inside ```RecyclerView``` ?
AutoAdapter's working perfectly with heterogeneous items.
You can add any descedent of ```Renderer``` to ```AutoAdapter```, for example it's not a problem to write the following:
```Java
...
mAutoAdapter.add(new FootballerRenderer());
mAutoAdapter.add(new BasketballerRenderer());
mAutoAdapter.add(new BoxerRenderer());
....
```
They all will draw their own layout.
