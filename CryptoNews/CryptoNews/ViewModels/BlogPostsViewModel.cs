using CryptoNews.Models;
using CryptoNews.Services;
using Plugin.Connectivity;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Xamarin.Forms;

namespace CryptoNews.ViewModels
{
    class BlogPostsViewModel : INotifyPropertyChanged
    {
        public ICommand RefreshCommand { get; set; }

        private List<BlogPost> _blogPosts;
        public List<BlogPost> BlogPosts
        {
            get { return _blogPosts; }
            set
            {
                _blogPosts = value;
                OnPropertyChanged();
            }
        }

        private List<FavouriteBlogPost> _favouritelogPosts;
        public List<FavouriteBlogPost> FavouriteBlogPosts
        {
            get { return _favouritelogPosts; }
            set
            {
                _favouritelogPosts = value;
                OnPropertyChanged();
            }
        }


        private bool _isBusy;
        public bool IsBusy
        {
            get { return _isBusy; }
            set
            {
                _isBusy = value;
                OnPropertyChanged();
            }
        }

        private bool _isRefreshing;
        public bool IsRefreshing
        {
            get { return _isRefreshing; }
            set
            {
                _isRefreshing = value;
                OnPropertyChanged();
            }
        }

        private INavigation Navigation;

        public BlogPostsViewModel(INavigation _Navigation)
        {
            RefreshCommand = new Command(RefreshAction);
            Navigation = _Navigation;
            IsBusy = true;
            BlogPosts = App.database.GetAllBlogPosts();
            InitAsync();
        }

        public BlogPostsViewModel(bool isFavourite)
        {
            if (isFavourite == true)
            {
                IsBusy = false;
                FavouriteBlogPosts = App.database.GetAllFavouriteBlogPosts();                
            }            
        }



        private async Task InitAsync()
        {
            if(CrossConnectivity.Current.IsConnected == true)
            {
                var service = new PostsRepository();
                var UpdatedPosts = service.GetAllPostsAsync();
                App.database.AddUpdatedBlogPosts(UpdatedPosts.Result);
                BlogPosts = App.database.GetAllBlogPosts();
            }
                       
            IsBusy = false;
        }

        public async void RefreshAction()
        {
            IsRefreshing = true;
            await InitAsync();
            IsRefreshing = false;
        }

        public event PropertyChangedEventHandler PropertyChanged;
        public virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            if (PropertyChanged != null)
            {
                PropertyChanged.Invoke(this, new PropertyChangedEventArgs(propertyName));
            }
        }

    }
}
