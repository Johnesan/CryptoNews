using CryptoNews.Models;
using CryptoNews.ViewModels;
using Plugin.Connectivity;
using Plugin.Share;
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

            if (CrossConnectivity.Current.IsConnected == false)
            {
                await DisplayAlert("Not Connected", "Sorry, you cannot read this post because you are not connected to the Internet", "Ok");
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

        public async void OnShareClicked(object sender, EventArgs e)
        {
            if (!CrossShare.IsSupported)
            {
                await DisplayAlert("Error", "This Device Platform does not support sharing", "OK");
                return;
            }

            var mi = ((MenuItem)sender);
            var favBlogPost = (FavouriteBlogPost)mi.CommandParameter;
            await CrossShare.Current.Share(new Plugin.Share.Abstractions.ShareMessage
            {
                Title = favBlogPost.Title,
                Text = favBlogPost.Excerpt + "..." + "(By " + favBlogPost.BlogWebsiteName + ")",
                Url = favBlogPost.Link
            });

        }
    }
}