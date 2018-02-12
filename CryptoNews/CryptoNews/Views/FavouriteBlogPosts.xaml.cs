using CryptoNews.Models;
using CryptoNews.ViewModels;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class FavouriteBlogPosts : ContentPage
    {
        public FavouriteBlogPosts()
        {
            InitializeComponent();
            BindingContext = new BlogPostsViewModel(true);
        }

        protected override void OnAppearing()
        {
            base.OnAppearing();
            ((App)App.Current).ResumeAtBlogWebsiteId = -1;
            BindingContext = new BlogPostsViewModel(true);
        }
        private async void OnSinglePostSelected(object sender, SelectedItemChangedEventArgs e)
        {
            if (e.SelectedItem == null)
            {
                return;
            }

            ((ListView)sender).SelectedItem = null;

            var SinglePost = e.SelectedItem as FavouriteBlogPost;   

            await Navigation.PushAsync(new SinglePost(SinglePost.Link));

        }

        private void DeleteClicked(object sender, EventArgs e)
        {
            var mi = ((MenuItem)sender);
            var favouriteBlogPost = (FavouriteBlogPost)mi.CommandParameter;
            App.database.DeleteFavouriteBlogPost(favouriteBlogPost);
            DisplayAlert("Successful", "Blog Post was successfully removed from your favourite list", "OK");
            BindingContext = new BlogPostsViewModel(true);

        }
    }
}