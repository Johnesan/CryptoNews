using CryptoNews.Models;
using CryptoNews.Services;
using CryptoNews.ViewModels;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class BlogPosts : ContentPage
    {
        

        public BlogPosts()
        {
          
            InitializeComponent();
            BindingContext = new BlogPostsViewModel(this.Navigation);
            
        }


        private async void OnSinglePostSelected(object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null)
            {
                return;
            }

            var SinglePost = e.SelectedItem as BlogPost;
            ((ListView)sender).SelectedItem = null;

            await Navigation.PushAsync(new SinglePost(SinglePost.Link));
           
        }


        public async void OnFavouriteClicked(object sender, EventArgs e)
        {
            var mi = ((MenuItem)sender);
            var blogPost = (BlogPost)mi.CommandParameter;
            var FavouriteBlogPost = new FavouriteBlogPost
            {
                Id = blogPost.Id,
                Title = blogPost.Title,
                BlogWebsiteName = blogPost.BlogWebsiteName,
                Date = blogPost.Date,
                Excerpt = blogPost.Excerpt,
                FeaturedImage = blogPost.FeaturedImage,
                Link = blogPost.Link,
                PrettyDate = blogPost.PrettyDate
            };
            await App.database.AddFavouriteBlogPost(FavouriteBlogPost);
            await DisplayAlert("Successful", "One post added to favourites", "OK");
        }

        //public void OnShareClicked(object sender, EventArgs e)
        //{
        //    var mi = ((FavouriteBlogPost)sender);
        //    //    var x = mi.CommandParameter.ToString();
        //    //    var SelectedFavouriteBlogPost = mi.CommandParameter as FavouriteBlogPost;
        //    //    var mi = ((MenuItem)sender);
        //    //    DisplayAlert("Delete Context Action", mi.CommandParameter + " delete context action", "OK");
        //    //
        //}
    }
}