using CryptoNews.Models;
using CryptoNews.Services;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace CryptoNews.ViewModels
{
    class BlogPostsViewModel : INotifyPropertyChanged
    {
        private ObservableCollection<BlogPost> _blogPosts;
        public ObservableCollection<BlogPost> BlogPosts
        {
            get { return _blogPosts; }
            set
            {
                _blogPosts = value;
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

        public BlogPostsViewModel()
        {
            IsBusy = true;
            BlogPosts = new ObservableCollection<BlogPost>(App.database.GetAllBlogPosts());
            InitAsync();
        }

        private async Task InitAsync()
        {
            var service = new PostsRepository();
            var UpdatedPosts = await service.GetAllPostsAsync();
            App.database.AddUpdatedBlogPosts(UpdatedPosts);
            BlogPosts = new ObservableCollection<BlogPost>(App.database.GetAllBlogPosts());
            IsBusy = false;
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
