using CryptoNews.Models;
using CryptoNews.Services;
using CryptoNews.ViewModels;
using Plugin.Connectivity;
using Plugin.Share;
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


            if (CrossConnectivity.Current.IsConnected == false)
            {
                await DisplayAlert("Not Connected", "Sorry, you cannot read this post because you are not connected to the Internet", "Ok");
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

        public async void OnShareClicked(object sender, EventArgs e)
        {
            if (!CrossShare.IsSupported)
            {
                await DisplayAlert("Error", "This Device Platform does not support sharing", "OK");
                return;
            }
            var mi = ((MenuItem)sender);
            var blogPost = (BlogPost)mi.CommandParameter;
            await CrossShare.Current.Share(new Plugin.Share.Abstractions.ShareMessage
            {
                Title = blogPost.Title,
                Text = blogPost.Excerpt + "..." + "(By " + blogPost.BlogWebsiteName + ")",
                Url = blogPost.Link
            });
        }

        public void SearchBar_TextChanged(object sender, TextChangedEventArgs e)
        {
            var viewModel = BindingContext as BlogPostsViewModel;
            var list = viewModel.BlogPosts;

            if (string.IsNullOrEmpty(e.NewTextValue))
            {
                BlogPostsListView.ItemsSource = list;
            }

            else
            {
               var newList = list.Where(x => x.Title.ToLower().Contains(e.NewTextValue.ToLower())).ToList();
                BlogPostsListView.ItemsSource = newList;
            }
        }
    }
}